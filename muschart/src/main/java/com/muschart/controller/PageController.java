package com.muschart.controller;

import static com.muschart.constants.DefaultConstants.*;
import static com.muschart.constants.UrlConstants.Page.Artist.*;
import static com.muschart.constants.UrlConstants.Page.Common.*;
import static com.muschart.constants.UrlConstants.Page.Genre.*;
import static com.muschart.constants.UrlConstants.Page.Track.*;
import static com.muschart.constants.UrlConstants.Page.User.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    @RequestMapping(value = {ARTISTS_URL}, method = RequestMethod.GET)
    public String defaultArtistsPage() {
        return REDIRECT + ARTISTS_URL + DEFAULT_PAGE;
    }

    @RequestMapping(value = {GENRES_URL}, method = RequestMethod.GET)
    public String defaultGenresPage() {
        return REDIRECT + GENRES_URL + DEFAULT_PAGE;
    }

    @RequestMapping(value = {"/", TRACKS_URL}, method = RequestMethod.GET)
    public String defaultTracksPage() {
        return REDIRECT + TRACKS_URL + DEFAULT_PAGE;
    }

    @RequestMapping(value = {ARTIST_GENRES_URL}, method = RequestMethod.GET)
    public String defaultArtistGenresPage() {
        return REDIRECT + ARTIST_GENRES_URL + DEFAULT_PAGE;
    }

    @RequestMapping(value = {ARTIST_TRACKS_URL}, method = RequestMethod.GET)
    public String defaultArtistTracksPage() {
        return REDIRECT + ARTIST_TRACKS_URL + DEFAULT_PAGE;
    }

    @RequestMapping(value = {GENRE_ARTISTS_URL}, method = RequestMethod.GET)
    public String defaultGenreArtistsPage() {
        return REDIRECT + GENRE_ARTISTS_URL + DEFAULT_PAGE;
    }

    @RequestMapping(value = {GENRE_TRACKS_URL}, method = RequestMethod.GET)
    public String defaultGenreTracksPage() {
        return REDIRECT + GENRE_TRACKS_URL + DEFAULT_PAGE;
    }

    @RequestMapping(value = {TRACK_ARTISTS_URL}, method = RequestMethod.GET)
    public String defaultTrackArtistsPage() {
        return REDIRECT + TRACK_ARTISTS_URL + DEFAULT_PAGE;
    }

    @RequestMapping(value = {TRACK_GENRES_URL}, method = RequestMethod.GET)
    public String defaultTrackGenresPage() {
        return REDIRECT + TRACK_GENRES_URL + DEFAULT_PAGE;
    }

    @RequestMapping(value = {USER_ARTISTS_URL}, method = RequestMethod.GET)
    public String defaultUserArtistsPage() {
        return REDIRECT + USER_ARTISTS_URL + DEFAULT_PAGE;
    }

    @RequestMapping(value = {USER_GENRES_URL}, method = RequestMethod.GET)
    public String defaultUserGenresPage() {
        return REDIRECT + USER_GENRES_URL + DEFAULT_PAGE;
    }

    @RequestMapping(value = {USER_TRACKS_URL}, method = RequestMethod.GET)
    public String defaultUserTracksPage() {
        return REDIRECT + USER_TRACKS_URL + DEFAULT_PAGE;
    }

    @RequestMapping(value = {LOGIN_URL, REGISTRATION_URL, SETTINGS_URL, PLAYLIST_URL, ARTIST_ADD_URL, GENRE_ADD_URL,
            TRACK_ADD_URL, ARTIST_FULL_URL, GENRE_FULL_URL, TRACK_FULL_URL}, method = RequestMethod.GET)
    public String withoutParams() {
        return DEFAULT_PATH;
    }

    @RequestMapping(value = {ARTISTS_URL, GENRES_URL, TRACKS_URL, GENRE_ARTISTS_URL, TRACK_ARTISTS_URL, USER_ARTISTS_URL,
            ARTIST_GENRES_URL, TRACK_GENRES_URL, USER_GENRES_URL, ARTIST_TRACKS_URL, GENRE_TRACKS_URL, USER_TRACKS_URL}, params = {"page"}, method = RequestMethod.GET)
    public String withParams() {
        return DEFAULT_PATH;
    }

}