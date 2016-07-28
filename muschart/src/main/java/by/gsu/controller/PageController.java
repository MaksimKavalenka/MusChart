package by.gsu.controller;

import static by.gsu.constants.PageConstants.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    @RequestMapping(value = {"/", TRACKS_URI}, method = RequestMethod.GET)
    public String home() {
        return REDIRECT + TRACKS_DEFAULT_URI;
    }

    @RequestMapping(value = {TRACKS_FULL_URI, ARTISTS_FULL_URI, LOGIN_URI,
            REGISTRATION_URI}, method = RequestMethod.GET)
    public String page() {
        return INDEX_PATH;
    }

}
