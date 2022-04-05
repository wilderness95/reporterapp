//package hu.wilderness.reporterapp.controller;
//
//import hu.wilderness.reporterapp.domain.User;
//import hu.wilderness.reporterapp.dto.RegistrationDto;
//
//import hu.wilderness.reporterapp.security.config.UsernameInUrlAuthenticationFailureHandler;
//import hu.wilderness.reporterapp.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpSession;
//import java.util.Map;
//
//@Controller
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/login")
//    public ModelAndView login(HttpSession httpSession) {
//        String lastUsername = (String) httpSession.getAttribute(UsernameInUrlAuthenticationFailureHandler.LAST_USERNAME_KEY);
//
//        if (lastUsername != null) {
//            System.out.println("username: " + lastUsername);
//            httpSession.removeAttribute(UsernameInUrlAuthenticationFailureHandler.LAST_USERNAME_KEY);
//        }
//
//        return new ModelAndView("login", "lastUsername", lastUsername);
//    }
//
//    @RequestMapping(value = "/")
//    public String registration(RegistrationDto registrationDto) {
//      //  userService.listUsers();
//        return "index";
//    }
//}
