package by.gsu.controller;

import static by.gsu.constants.PageConstants.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return REDIRECT + TRACKS_URI;
    }

    @RequestMapping(value = {TRACKS_URI, ARTISTS_URI, LOGIN_URI,
            REGISTRATION_URI}, method = RequestMethod.GET)
    public String page() {
        return INDEX_PATH;
    }

}
