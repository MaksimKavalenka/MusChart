package by.gsu.constants;

public enum ActionConstants {

    //TRACK, ACTOR, TAG, TRACK_ACTOR, TRACK_TAG, ACTOR_TAG, LOGIN, USER, USER_TRACK, USER_ACTOR, USER_TAG, DELETE_TRACK, DELETE_ACTOR, DELETE_TAG, DELETE_TRACK_ACTOR, DELETE_TRACK_TAG, DELETE_ACTOR_TAG;

    TRACKS(UriConstants.Pages.TRACKS_PAGE_URI, PathConstants.Pages.TRACK_PAGE_PATH, UriConstants.Pages.TRACKS_PAGE_URI),
    ADD_TRACK(UriConstants.Forms.ADD_TRACK_FORM_URI, PathConstants.Forms.ADD_TRACK_FORM_PATH, UriConstants.Forms.ADD_TRACK_FORM_URI),
    ARTISTS(UriConstants.Pages.ARTISTS_PAGE_URI, PathConstants.Pages.ARTIST_PAGE_PATH, UriConstants.Pages.ARTISTS_PAGE_URI),
    ADD_ARTIST(UriConstants.Forms.ADD_ARTIST_FORM_URI, PathConstants.Forms.ADD_ARTIST_FORM_PATH, UriConstants.Forms.ADD_ARTIST_FORM_URI),
    GENRES(UriConstants.Pages.GENRES_PAGE_URI, PathConstants.Pages.GENRE_PAGE_PATH, UriConstants.Pages.GENRES_PAGE_URI),
    ADD_GENRE(UriConstants.Forms.ADD_GENRE_FORM_URI, PathConstants.Forms.ADD_GENRE_FORM_PATH, UriConstants.Forms.ADD_GENRE_FORM_URI),
    LOGIN(UriConstants.Forms.LOGIN_FORM_URI, PathConstants.Forms.LOGIN_FORM_PATH, UriConstants.Pages.TRACKS_PAGE_URI),
    LOGOUT(UriConstants.Pages.TRACKS_PAGE_URI, PathConstants.Pages.TRACK_PAGE_PATH, UriConstants.Pages.TRACKS_PAGE_URI),
    REGISTRATION(UriConstants.Forms.REGISTRATION_FORM_URI, PathConstants.Forms.REGISTRATION_FORM_PATH, UriConstants.Pages.TRACKS_PAGE_URI);

    private String uri;
    private String path;
    private String location;

    private ActionConstants(final String uri, final String path, final String location) {
        this.uri = uri;
        this.path = path;
        this.location = location;
    }

    public String getUri() {
        return uri;
    }

    public String getPath() {
        return path;
    }

    public String getLocation() {
        return location;
    }

}
