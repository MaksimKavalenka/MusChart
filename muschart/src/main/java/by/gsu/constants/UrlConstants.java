package by.gsu.constants;

public abstract class UrlConstants {

    public static final String JSON_EXT = ".json";
    public static final String ID_KEY   = "/{id:[0-9]{1,}}";
    public static final String PAGE_KEY = "/{page:[0-9]{1,}}";

    public abstract class Page {

        private static final String ADD_OPERATION     = "/add";

        public static final String  LOGIN_URL         = "/login";
        public static final String  REGISTRATION_URL  = "/register";
        public static final String  SETTINGS_URL      = "/settings";
        public static final String  PLAYLIST_URL      = "/playlist";

        public static final String  ARTISTS_URL       = "/artists";
        public static final String  GENRES_URL        = "/genres";
        public static final String  TRACKS_URL        = "/tracks";

        public static final String  ARTIST_URL        = "/artist";
        public static final String  ARTIST_ADD_URL    = ARTIST_URL + ADD_OPERATION;
        public static final String  ARTIST_FULL_URL   = ARTIST_URL + ID_KEY;
        public static final String  ARTIST_GENRES_URL = ARTIST_URL + ID_KEY + GENRES_URL;
        public static final String  ARTIST_TRACKS_URL = ARTIST_URL + ID_KEY + TRACKS_URL;

        public static final String  GENRE_URL         = "/genre";
        public static final String  GENRE_ADD_URL     = GENRE_URL + ADD_OPERATION;
        public static final String  GENRE_FULL_URL    = GENRE_URL + ID_KEY;
        public static final String  GENRE_ARTISTS_URL = GENRE_URL + ID_KEY + ARTISTS_URL;
        public static final String  GENRE_TRACKS_URL  = GENRE_URL + ID_KEY + TRACKS_URL;

        public static final String  TRACK_URL         = "/track";
        public static final String  TRACK_ADD_URL     = TRACK_URL + ADD_OPERATION;
        public static final String  TRACK_FULL_URL    = TRACK_URL + ID_KEY;
        public static final String  TRACK_ARTISTS_URL = TRACK_URL + ID_KEY + ARTISTS_URL;
        public static final String  TRACK_GENRES_URL  = TRACK_URL + ID_KEY + GENRES_URL;

        public static final String  USER_URL          = "/user";
        public static final String  USER_ARTISTS_URL  = USER_URL + ARTISTS_URL;
        public static final String  USER_GENRES_URL   = USER_URL + GENRES_URL;
        public static final String  USER_TRACKS_URL   = USER_URL + TRACKS_URL;

    }

    public abstract class Rest {

        public static final String ARTISTS_URL = "/artists";
        public static final String GENRES_URL  = "/genres";
        public static final String TRACKS_URL  = "/tracks";
        public static final String UNITS_URL   = "/units";
        public static final String UPLOAD_URL  = "/upload";
        public static final String USERS_URL   = "/users";

    }

}
