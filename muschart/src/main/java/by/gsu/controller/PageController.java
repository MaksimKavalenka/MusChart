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

    @RequestMapping(value = {GENRE_ARTISTS_URI}, method = RequestMethod.GET)
    public String genreArtistsPage() {
        return REDIRECT + GENRE_ARTISTS_DEFAULT_URI;
    }

    @RequestMapping(value = {TRACK_ARTISTS_URI}, method = RequestMethod.GET)
    public String trackArtistsPage() {
        return REDIRECT + TRACK_ARTISTS_DEFAULT_URI;
    }

    @RequestMapping(value = {USER_ARTISTS_URI}, method = RequestMethod.GET)
    public String userArtistsPage() {
        return REDIRECT + USER_ARTISTS_DEFAULT_URI;
    }

    @RequestMapping(value = {ARTIST_GENRES_URI}, method = RequestMethod.GET)
    public String artistGenresPage() {
        return REDIRECT + ARTIST_GENRES_DEFAULT_URI;
    }

    @RequestMapping(value = {TRACK_GENRES_URI}, method = RequestMethod.GET)
    public String trackGenresPage() {
        return REDIRECT + TRACK_GENRES_DEFAULT_URI;
    }

    @RequestMapping(value = {USER_GENRES_URI}, method = RequestMethod.GET)
    public String userGenresPage() {
        return REDIRECT + USER_GENRES_DEFAULT_URI;
    }

    @RequestMapping(value = {ARTIST_TRACKS_URI}, method = RequestMethod.GET)
    public String artistTracksPage() {
        return REDIRECT + ARTIST_TRACKS_DEFAULT_URI;
    }

    @RequestMapping(value = {GENRE_TRACKS_URI}, method = RequestMethod.GET)
    public String genreTracksPage() {
        return REDIRECT + GENRE_TRACKS_DEFAULT_URI;
    }

    @RequestMapping(value = {USER_TRACKS_URI}, method = RequestMethod.GET)
    public String userTracksPage() {
        return REDIRECT + USER_TRACKS_DEFAULT_URI;
    }

    @RequestMapping(value = {SETTINGS_URI, LOGIN_URI, REGISTRATION_URI, ARTISTS_FULL_URI,
            GENRES_FULL_URI, TRACKS_FULL_URI, ARTIST_URI, GENRE_URI, TRACK_URI,
            GENRE_ARTISTS_FULL_URI, TRACK_ARTISTS_FULL_URI, USER_ARTISTS_FULL_URI,
            ARTIST_GENRES_FULL_URI, TRACK_GENRES_FULL_URI, USER_GENRES_FULL_URI,
            ARTIST_TRACKS_FULL_URI, GENRE_TRACKS_FULL_URI, USER_TRACKS_FULL_URI, ARTIST_ADD_URI,
            GENRE_ADD_URI, TRACK_ADD_URI}, method = RequestMethod.GET)
    public String page() {
        return INDEX_PATH;
    }

}
