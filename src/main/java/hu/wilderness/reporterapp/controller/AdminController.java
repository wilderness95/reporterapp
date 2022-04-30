package hu.wilderness.reporterapp.controller;

import hu.wilderness.reporterapp.dto.RegistrationDto;
import hu.wilderness.reporterapp.dto.UserDto;
import hu.wilderness.reporterapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AdminController {

    @Autowired
    UserService userService;


    @RequestMapping("/admin/user")
    public String admin(){
        return "/admin/user";
    }

    @RequestMapping("/admin/newUser")
    public String addUser(Model model){
        model.addAttribute("user", new UserDto());
        return "/admin/newUser";
    }

    @PostMapping(value = "/addUser")
    public String newUser(UserDto userDto) {
        userService.sendFirstLoginMail(userDto);

        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/admin/setpassword/{uuId}",method = RequestMethod.GET)
    public ModelAndView emailConfirm(@PathVariable String uuId){
        String message;
        try {
        //    userService.sendFirstLoginMail()
        } catch (Exception e) {
            System.out.println("hiba: "+ e);
            message = e.getMessage();
        }

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/email/emailconfirmed");
        return mv;
    }

}
