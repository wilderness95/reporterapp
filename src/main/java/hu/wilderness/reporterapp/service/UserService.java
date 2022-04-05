package hu.wilderness.reporterapp.service;


import hu.wilderness.reporterapp.dataacces.dao.UserJdbcDao;
import hu.wilderness.reporterapp.domain.User;
import hu.wilderness.reporterapp.dto.RegistrationDto;
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


    //TODO sec conf-ban kiszervezni
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());


    public void testToken() {
        User u = userJdbcDao.findByEmailAddress("teszt");
        log.debug(u.toString());
        tokenService.createNew(u);
        log.debug("Sikeres Token létrehozás");
    }

    public List<User> listUsers() {
//        Object user = SecurityContextHolder.getContext()
//                .getAuthentication().getPrincipal();
//        log.debug("Logged in user: {}", user.toString());
        List<User> userList = userJdbcDao.findAll();
        log.debug(userList.toString());
        return userList;
    }


    public User createNew(RegistrationDto registrationDto) {
        User user = new User();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(registrationDto, user);

        user.setName(registrationDto.getName());
        user.setNickName(registrationDto.getNickName());
        user.setEmail(registrationDto.getEmail());
//	        user.setPassword(registrationDto.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(registrationDto.getPassword()));
        user.setActive(false);
        user.setCreatedDate(new Date());
        user.setBirthDate(null);
        user.setLastLoggedIn(null);

        user = save(user);
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