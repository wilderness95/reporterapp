package hu.wilderness.reporterapp.service;


import hu.wilderness.reporterapp.dataacces.dao.UserJdbcDao;
import hu.wilderness.reporterapp.domain.Token;
import hu.wilderness.reporterapp.domain.User;
import hu.wilderness.reporterapp.dto.NewPasswordDto;
import hu.wilderness.reporterapp.dto.UserDto;
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
    public List<User> listActiveUsers() {
        List<User> userList = userJdbcDao.findByActive(true);
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
        user.setRoleName( userDto.getRoleName().equals("ROLE_ADMIN") ? User.UserRole.ROLE_ADMIN : User.UserRole.ROLE_USER);
       // user.setPassword(bCryptPasswordEncoder.encode(passwordGenerator.generateRandomPassword(8)));
        user.setActive(false);
        user.setCreatedDate(new Date());
        user.setLastLoggedIn(null);
        user = save(user);


        log.debug("create a new user: \n\n " + user.toString());
        return user;
    }

    public void setUserActive(User user, Boolean active){
        System.out.println(user.toString());
        user.setActive(active);
        save(user);
    }

    public void sendFirstLoginMail(UserDto userDto){
        User user = createNew(userDto);
        Token token = tokenService.createNew("FIRSTPASSWORD", user);
        emailService.sendRequestMailToActivateAccount(user.getEmail(), token.getToken());
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

public NewPasswordDto passDto(String uuid){
        NewPasswordDto npdto = new NewPasswordDto();
        npdto.setUuid(uuid);
        return npdto;
}
    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        User user = userJdbcDao.findByEmailAddress(emailAddress);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + emailAddress);
        }

        return new CustomUserDetails(user);
    }

public boolean isTheSamePassword(String password1, String password2){
        return password1.equals(password2)? true : false;
}
    public void setAccountActive (NewPasswordDto newPasswordDto) {
        Token token = tokenService.getToken2(newPasswordDto.getUuid());
        User user = getUser(token.getUser().getId());
        if (isTheSamePassword(newPasswordDto.getPassword1(), newPasswordDto.getPassword2())){
            user.setPassword(bCryptPasswordEncoder.encode(newPasswordDto.getPassword1()));
            user.setActive(true);
            save(user);
        }
    }

    public User UserDtoToUser(UserDto udt){
        User user = getUser(udt.getId());

        user.setFirstName(udt.getFirstName());
        user.setLastName(udt.getLastName());
        user.setCounty(udt.getCounty());
        user.setEmail(udt.getEmail());
        user.setPhoneNumber(udt.getPhoneNumber());
        return user;
    }

}