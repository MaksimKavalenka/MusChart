package by.gsu.controller;

import static by.gsu.constants.PageConstants.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    @RequestMapping(value = {ARTISTS_URI}, method = RequestMethod.GET)
    public String artistsPage() {
        return REDIRECT + ARTISTS_DEFAULT_URI;
    }

    @RequestMapping(value = {GENRES_URI}, method = RequestMethod.GET)
    public String genresPage() {
        return REDIRECT + GENRES_DEFAULT_URI;
    }

    @RequestMapping(value = {"/", TRACKS_URI}, method = RequestMethod.GET)
    public String tracksPage() {
        return REDIRECT + TRACKS_DEFAULT_URI;
    }

    @RequestMapping(value = {SETTINGS_URI, LOGIN_URI, REGISTRATION_URI, ARTISTS_FULL_URI,
            GENRES_FULL_URI, TRACKS_FULL_URI, ARTISTS_ADD_URI, GENRES_ADD_URI,
            TRACKS_ADD_URI}, method = RequestMethod.GET)
    public String page() {
        return INDEX_PATH;
    }

}
