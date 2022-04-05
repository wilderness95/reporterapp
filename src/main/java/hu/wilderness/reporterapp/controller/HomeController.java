package hu.wilderness.reporterapp.controller;

import hu.wilderness.reporterapp.security.config.UsernameInUrlAuthenticationFailureHandler;
import hu.wilderness.reporterapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public ModelAndView login(HttpSession httpSession) {
        String lastUsername = (String) httpSession.getAttribute(UsernameInUrlAuthenticationFailureHandler.LAST_USERNAME_KEY);

        if (lastUsername != null) {
            System.out.println("username: " + lastUsername);
            httpSession.removeAttribute(UsernameInUrlAuthenticationFailureHandler.LAST_USERNAME_KEY);
        }

        return new ModelAndView("/auth/login", "lastUsername", lastUsername);
    }


    @GetMapping("/report")
    public String bloggers(){
        return "report";
    }

    @GetMapping("/info")
    public String stories(Model model){
        model.addAttribute("users",userService.listUsers());
        return "info";
    }

    @RequestMapping("/registration")
    public String registration(Model model){
        return "registration";
    }
}
