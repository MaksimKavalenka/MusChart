package by.gsu.constants;

public abstract class UrlConstants {

    private static final String ID_KEY            = "/{id:[0-9]{1,}}";

    private static final String ADD_OPERATION     = "/add";

    private static final String ARTIST_PATH       = "/artist";
    private static final String GENRE_PATH        = "/genre";
    private static final String TRACK_PATH        = "/track";
    private static final String USER_PATH         = "/user";

    public static final String  LOGIN_URL         = "/login";
    public static final String  REGISTRATION_URL  = "/register";
    public static final String  SETTINGS_URL      = "/settings";
    public static final String  PLAYLIST_URL      = "/playlist";

    public static final String  ARTIST_ADD_URL    = ARTIST_PATH + ADD_OPERATION;
    public static final String  GENRE_ADD_URL     = GENRE_PATH + ADD_OPERATION;
    public static final String  TRACK_ADD_URL     = TRACK_PATH + ADD_OPERATION;

    public static final String  ARTIST_URL        = "/artist" + ID_KEY;
    public static final String  GENRE_URL         = "/genre" + ID_KEY;
    public static final String  TRACK_URL         = "/track" + ID_KEY;
    public static final String  ARTISTS_URL       = "/artists";
    public static final String  GENRES_URL        = "/genres";
    public static final String  TRACKS_URL        = "/tracks";

    public static final String  GENRE_ARTISTS_URL = GENRE_PATH + ID_KEY + ARTISTS_URL;
    public static final String  TRACK_ARTISTS_URL = TRACK_PATH + ID_KEY + ARTISTS_URL;
    public static final String  USER_ARTISTS_URL  = USER_PATH + ARTISTS_URL;
    public static final String  ARTIST_GENRES_URL = ARTIST_PATH + ID_KEY + GENRES_URL;
    public static final String  TRACK_GENRES_URL  = TRACK_PATH + ID_KEY + GENRES_URL;
    public static final String  USER_GENRES_URL   = USER_PATH + GENRES_URL;
    public static final String  ARTIST_TRACKS_URL = ARTIST_PATH + ID_KEY + TRACKS_URL;
    public static final String  GENRE_TRACKS_URL  = GENRE_PATH + ID_KEY + TRACKS_URL;
    public static final String  USER_TRACKS_URL   = USER_PATH + TRACKS_URL;

}
