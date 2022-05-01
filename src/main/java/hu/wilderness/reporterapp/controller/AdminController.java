package hu.wilderness.reporterapp.controller;

import hu.wilderness.reporterapp.domain.User;
import hu.wilderness.reporterapp.dto.NewPasswordDto;
import hu.wilderness.reporterapp.dto.UserDto;
import hu.wilderness.reporterapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
public class AdminController {

    @Autowired
    UserService userService;


    @RequestMapping("/admin/user")
    public String admin(Model model){
        model.addAttribute("users",userService.listActiveUsers());
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

    @GetMapping(value = "/admin/deleteuser/{id}")
    public String deleteUser(@PathVariable(value = "id") long id) {
        userService.setUserActive(userService.getUser(id),false );
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/updateuser/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

        User user = userService.getUser(id);
        System.out.println("\n\n Betöltéskor: " +user.toString());


        model.addAttribute("user", user);
        return "/admin/updateUser";
    }

    @PostMapping(value = "/admin/saveUser")
    public String saveUser(UserDto userDto) {
        System.out.println(userDto.toString());

        userService.save(userService.UserDtoToUser(userDto));

        return "redirect:/admin/user";
    }


//    @RequestMapping(value = "/admin/setpassword/{uuId}",method = RequestMethod.GET)
//    public ModelAndView emailConfirm(@PathVariable String uuId){
//        String message;
//        try {
//           userService.setAccountActive(uuId);
//        } catch (Exception e) {
//            System.out.println("hiba: "+ e);
//            message = e.getMessage();
//        }
//
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("/email/emailconfirmed");
//        return mv;
//    }
    @RequestMapping(value = "/admin/setpassword/{uuId}",method = RequestMethod.GET)
    public String emailConfirm(Model model, @PathVariable("uuId") String uuId){

        NewPasswordDto npdto = userService.passDto(uuId);
        System.out.println(npdto.toString());
        model.addAttribute("password", npdto);
            return "/admin/newpassword";//file név

    }
    @PostMapping(value = "/admin/createPassword")
    public ModelAndView newPassword(NewPasswordDto newPasswordDto) {
//        String siteURL = request.getRequestURL().toString();
//        System.out.println(siteURL);
        System.out.println(newPasswordDto.toString());
        userService.setAccountActive(newPasswordDto);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/email/emailconfirmed");
        return mv;

    }

}
