package hu.wilderness.reporterapp.controller;

import hu.wilderness.reporterapp.domain.Report;
import hu.wilderness.reporterapp.domain.User;
import hu.wilderness.reporterapp.dto.NewPasswordDto;
import hu.wilderness.reporterapp.dto.ReportDto;
import hu.wilderness.reporterapp.dto.UserDto;
import hu.wilderness.reporterapp.service.ReportService;
import hu.wilderness.reporterapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;


@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    ReportService reportService;


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

    @GetMapping("/admin/showuser/{id}")
    public String showUserDetails(@PathVariable(value = "id") long id, Model model) {

        User user = userService.getUser(id);

        model.addAttribute("user", user);
        return "/admin/userdetails";
    }



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

    @RequestMapping("/admin/report")
    public String reports(Model model){
        model.addAttribute("reports",reportService.getAllActiveReport());
        return "/admin/report";
    }
    @RequestMapping("/admin/case")
    public String cases(){

        return "/admin/case";
    }


    @GetMapping(value = "/admin/deletereport/{id}")
    public String deleteReport(@PathVariable(value = "id") long id,  RedirectAttributes redirAttrs) {
        reportService.setActiveState(reportService.getReportById(id),false);
        redirAttrs.addFlashAttribute("success", "A bejelentést sikeresen törölted!");
        return "redirect:/admin/report";
    }

    @GetMapping("/admin/showreport/{id}")
    public String showReportDetails(@PathVariable(value = "id") long id, Model model) {

       Report report = reportService.getReportById(id);

        model.addAttribute("report", report);
        return "/admin/reportdetails";
    }

    @GetMapping("/admin/updatereport/{id}")
    public String updateReport(@PathVariable(value = "id") long id, Model model) {

       Report report = reportService.getReportById(id);


        model.addAttribute("report", report);
        return "/admin/updatereports";
    }

    @PostMapping(value = "/admin/updateReport")
    public String updateReport(ReportDto reportDto) {

        reportService.save(reportService.reportDtoToReport(reportDto));


        return "redirect:/admin/report";
    }
}
