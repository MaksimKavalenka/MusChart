package by.gsu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.gsu.constants.PageConstants;

@Controller
public class PageController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "redirect:" + PageConstants.MAIN;
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String track() {
        return PageConstants.MAIN;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return PageConstants.LOGIN;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration() {
        return PageConstants.REGIGTRATION;
    }

}
