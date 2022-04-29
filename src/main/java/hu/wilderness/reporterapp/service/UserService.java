package hu.wilderness.reporterapp.service;


import hu.wilderness.reporterapp.dataacces.dao.UserJdbcDao;
import hu.wilderness.reporterapp.domain.Report;
import hu.wilderness.reporterapp.domain.Token;
import hu.wilderness.reporterapp.domain.User;
import hu.wilderness.reporterapp.dto.RegistrationDto;
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

    public void sendActivationEmailForUser(User user){
        Token token = tokenService.createNew("FIRSTPASSWORD");
        token.setUser(user);
        tokenService.save(token);
        emailService.sendRequestMailToActivateAccount(user.getEmail(),token.getToken());
    }

    public void setUserActive(User user, Boolean active){
        user.setActive(active);
        save(user);
    }


    public void setSuccessfulState(String tokenUuid) {
        Token token = tokenService.getToken(tokenUuid);
        System.out.println("token:    " +token.toString());
        User user = getUserByToken(token.getId());
        System.out.println(user.toString());
        Date currentDate = new Date();

        if(token.isActive() && !token.isSuccessful()){
            tokenService.setActiveAndSuccessfulDate(token,false,true,currentDate);
            setUserActive(user,true);
            log.debug("A megerősítés sikeres volt....");

        }else if(user.getActive()){
            log.debug("A megerősítés már sikeres volt");
        }
        else{
            setUserActive(user,false);
            log.debug("A megerősítés sikertelen volt...");
        }
    }

    private User getUserByToken(Long id) { return userJdbcDao.findByToken(id);
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

        sendActivationEmailForUser(user);
        log.debug("create a new user: " + user.toString());
        return user;
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