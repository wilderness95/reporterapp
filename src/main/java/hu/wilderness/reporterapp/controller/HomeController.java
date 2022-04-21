package hu.wilderness.reporterapp.controller;

import hu.wilderness.reporterapp.dto.ReportDto;
import hu.wilderness.reporterapp.security.config.UsernameInUrlAuthenticationFailureHandler;
import hu.wilderness.reporterapp.service.ReportService;
import hu.wilderness.reporterapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    UserService userService;
    @Autowired
    ReportService reportService;

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



    @GetMapping("/info")
    public String stories(Model model){
        model.addAttribute("users",userService.listUsers());
        return "info";
    }

    @RequestMapping("/report")
    public String report(Model model){
        model.addAttribute("report", new ReportDto());
        return "report";
    }

    @PostMapping(value = "/reg")
    public String report(ReportDto reportDto, HttpServletRequest request, @RequestParam("image") MultipartFile multipartFile) {
        reportService.createNew(reportDto, request, multipartFile);

        return "redirect:/report";
    }



    @RequestMapping(value = "/emailconfirm/{uuId}",method = RequestMethod.GET)
    public ModelAndView emailConfirm(@PathVariable String uuId){
        String message;
        try {
            reportService.setSuccessfulState(uuId);
        } catch (Exception e) {
            System.out.println("hiba: "+ e);
            message = e.getMessage();
        }

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/email/emailconfirmed");
        return mv;
    }
}
