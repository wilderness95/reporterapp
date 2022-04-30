package hu.wilderness.reporterapp.service;


import hu.wilderness.reporterapp.dataacces.dao.UserJdbcDao;
import hu.wilderness.reporterapp.domain.Token;
import hu.wilderness.reporterapp.domain.User;
import hu.wilderness.reporterapp.dto.UserDto;
import hu.wilderness.reporterapp.utils.passwordGenerator;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TokenService tokenService;

    @Autowired
    UserJdbcDao userJdbcDao;

    @Autowired
    EmailService emailService;


    //TODO sec conf-ban kiszervezni
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());



    public List<User> listUsers() {
//        Object user = SecurityContextHolder.getContext()
//                .getAuthentication().getPrincipal();
//        log.debug("Logged in user: {}", user.toString());
        List<User> userList = userJdbcDao.findAll();
        log.debug(userList.toString());
        return userList;
    }

    public User getUser(long id){
        return userJdbcDao.findById(id);
    }



    //TODO ellenőrizni, hogy a mail cím létezik-e már
    public User createNew(UserDto userDto) {
        User user = new User();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(userDto, user);

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setCounty(userDto.getCounty());
        user.setRoleName( userDto.getRole().equals("ROLE_ADMIN") ? User.UserRole.ROLE_ADMIN : User.UserRole.ROLE_USER);
        user.setPassword(bCryptPasswordEncoder.encode(passwordGenerator.generateRandomPassword(8)));
        user.setActive(false);
        user.setCreatedDate(new Date());
        user.setLastLoggedIn(null);
        user = save(user);


        log.debug("create a new user: \n\n " + user.toString());
        return user;
    }

    public void sendFirstLoginMail(UserDto userDto){
        User user = createNew(userDto);
        System.out.println("\n\n\n "+ userJdbcDao.findById(user.getId())+"\n\n\n");
        Token token = tokenService.createNew("FIRSTPASSWORD", user);
        System.out.println(tokenService.getToken2(token.getToken()));
    }


    public User save(User user) {
        if (user.getId() == null) {
            user = userJdbcDao.insert(user);
            log.debug("user has been successfully saved");
        } else {
            user = userJdbcDao.update(user);
            log.debug("user has been successfully updated");
        }
        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        User user = userJdbcDao.findByEmailAddress(emailAddress);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + emailAddress);
        }

        return new CustomUserDetails(user);
    }


}