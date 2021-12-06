package hu.wilderness.reporterapp.controller;

import hu.wilderness.reporterapp.dto.RegistrationDto;
import hu.wilderness.reporterapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;


    @GetMapping("/lofasz")
    public String view() {
        return "reg";//file név
    }


    @RequestMapping(value = "/lofasz", method = RequestMethod.POST)
    public String registration(RegistrationDto registrationDto) {

        userService.createNew(registrationDto);
        return "reg";
    }
}