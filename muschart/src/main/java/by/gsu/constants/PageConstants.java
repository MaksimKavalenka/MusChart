package by.gsu.constants;

public abstract class PageConstants {

    public static final String  REDIRECT                  = "redirect:";
    public static final String  INDEX_PATH                = "/index";

    private static final String ADD_OPERATION             = "/add";
    private static final String PAGE_OPERATION            = "/page";

    private static final String ID_PATTERN                = "/{id:[0-9]{1,}}";
    private static final String PAGE_PATTERN              = "/{page:[0-9]{1,}}";
    private static final String PAGE_DEFAULT              = "/1";

    public static final String  SETTINGS_URI              = "/settings";
    public static final String  LOGIN_URI                 = "/login";
    public static final String  REGISTRATION_URI          = "/register";

    public static final String  ARTISTS_URI               = "/artists";
    public static final String  GENRES_URI                = "/genres";
    public static final String  TRACKS_URI                = "/tracks";
    public static final String  ARTIST_URI                = "/artist" + ID_PATTERN;
    public static final String  GENRE_URI                 = "/genre" + ID_PATTERN;
    public static final String  TRACK_URI                 = "/track" + ID_PATTERN;
    public static final String  USER_URI                  = "/user";

    public static final String  GENRE_ARTISTS_URI         = GENRE_URI + ARTISTS_URI;
    public static final String  TRACK_ARTISTS_URI         = TRACK_URI + ARTISTS_URI;
    public static final String  USER_ARTISTS_URI          = USER_URI + ARTISTS_URI;
    public static final String  ARTIST_GENRES_URI         = ARTIST_URI + GENRES_URI;
    public static final String  TRACK_GENRES_URI          = TRACK_URI + GENRES_URI;
    public static final String  USER_GENRES_URI           = USER_URI + GENRES_URI;
    public static final String  ARTIST_TRACKS_URI         = ARTIST_URI + TRACKS_URI;
    public static final String  GENRE_TRACKS_URI          = GENRE_URI + TRACKS_URI;
    public static final String  USER_TRACKS_URI           = USER_URI + TRACKS_URI;

    public static final String  ARTIST_ADD_URI            = ARTIST_URI + ADD_OPERATION;
    public static final String  GENRE_ADD_URI             = GENRE_URI + ADD_OPERATION;
    public static final String  TRACK_ADD_URI             = TRACK_URI + ADD_OPERATION;

    public static final String  ARTISTS_DEFAULT_URI       = ARTISTS_URI + PAGE_OPERATION + PAGE_DEFAULT;
    public static final String  GENRES_DEFAULT_URI        = GENRES_URI + PAGE_OPERATION + PAGE_DEFAULT;
    public static final String  TRACKS_DEFAULT_URI        = TRACKS_URI + PAGE_OPERATION + PAGE_DEFAULT;
    
    public static final String  GENRE_ARTISTS_DEFAULT_URI = GENRE_ARTISTS_URI + PAGE_OPERATION + PAGE_DEFAULT;
    public static final String  TRACK_ARTISTS_DEFAULT_URI = TRACK_ARTISTS_URI + PAGE_OPERATION + PAGE_DEFAULT;
    public static final String  USER_ARTISTS_DEFAULT_URI  = USER_ARTISTS_URI + PAGE_OPERATION + PAGE_DEFAULT;
    public static final String  ARTIST_GENRES_DEFAULT_URI = ARTIST_GENRES_URI + PAGE_OPERATION + PAGE_DEFAULT;
    public static final String  TRACK_GENRES_DEFAULT_URI  = TRACK_GENRES_URI + PAGE_OPERATION + PAGE_DEFAULT;
    public static final String  USER_GENRES_DEFAULT_URI   = USER_GENRES_URI + PAGE_OPERATION + PAGE_DEFAULT;
    public static final String  ARTIST_TRACKS_DEFAULT_URI = ARTIST_TRACKS_URI + PAGE_OPERATION + PAGE_DEFAULT;
    public static final String  GENRE_TRACKS_DEFAULT_URI  = GENRE_TRACKS_URI + PAGE_OPERATION + PAGE_DEFAULT;
    public static final String  USER_TRACKS_DEFAULT_URI   = USER_GENRES_URI + PAGE_OPERATION + PAGE_DEFAULT;

    public static final String  ARTISTS_FULL_URI          = ARTISTS_URI + PAGE_OPERATION + PAGE_PATTERN;
    public static final String  GENRES_FULL_URI           = GENRES_URI + PAGE_OPERATION + PAGE_PATTERN;
    public static final String  TRACKS_FULL_URI           = TRACKS_URI + PAGE_OPERATION + PAGE_PATTERN;
    
    public static final String  GENRE_ARTISTS_FULL_URI    = GENRE_ARTISTS_URI + PAGE_OPERATION + PAGE_PATTERN;
    public static final String  TRACK_ARTISTS_FULL_URI    = TRACK_ARTISTS_URI + PAGE_OPERATION + PAGE_PATTERN;
    public static final String  USER_ARTISTS_FULL_URI     = USER_ARTISTS_URI + PAGE_OPERATION + PAGE_PATTERN;
    public static final String  ARTIST_GENRES_FULL_URI    = ARTIST_GENRES_URI + PAGE_OPERATION + PAGE_PATTERN;
    public static final String  TRACK_GENRES_FULL_URI     = TRACK_GENRES_URI + PAGE_OPERATION + PAGE_PATTERN;
    public static final String  USER_GENRES_FULL_URI      = USER_GENRES_URI + PAGE_OPERATION + PAGE_PATTERN;
    public static final String  ARTIST_TRACKS_FULL_URI    = ARTIST_TRACKS_URI + PAGE_OPERATION + PAGE_PATTERN;
    public static final String  GENRE_TRACKS_FULL_URI     = GENRE_TRACKS_URI + PAGE_OPERATION + PAGE_PATTERN;
    public static final String  USER_TRACKS_FULL_URI      = USER_TRACKS_URI + PAGE_OPERATION + PAGE_PATTERN;

}
