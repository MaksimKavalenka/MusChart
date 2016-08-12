package by.gsu.constants;

public abstract class PageConstants {

    public static final String  REDIRECT            = "redirect:";
    public static final String  INDEX_PATH          = "/index";

    private static final String ADD_OPERATION       = "/add";
    private static final String ARTIST_OPERATION    = "/artist";
    private static final String GENRE_OPERATION     = "/genre";
    private static final String PAGE_OPERATION      = "/page";
    private static final String TRACK_OPERATION     = "/track";

    private static final String ID_PATTERN          = "/{id:[0-9]{1,}}";
    private static final String PAGE_PATTERN        = "/{page:[0-9]{1,}}";
    private static final String PAGE_DEFAULT        = "/1";

    public static final String  SETTINGS_URI        = "/settings";
    public static final String  LOGIN_URI           = "/login";
    public static final String  REGISTRATION_URI    = "/register";

    public static final String  ARTISTS_URI         = "/artists";
    public static final String  GENRES_URI          = "/genres";
    public static final String  TRACKS_URI          = "/tracks";

    public static final String  TRACK_ARTISTS_URI   = ARTISTS_URI + TRACK_OPERATION + ID_PATTERN;
    public static final String  GENRE_ARTISTS_URI   = ARTISTS_URI + GENRE_OPERATION + ID_PATTERN;
    public static final String  TRACK_GENRES_URI    = GENRES_URI + TRACK_OPERATION + ID_PATTERN;
    public static final String  ARTIST_GENRES_URI   = GENRES_URI + ADD_OPERATION + ID_PATTERN;
    public static final String  ARTIST_TRACKS_URI   = TRACKS_URI + ARTIST_OPERATION + ID_PATTERN;
    public static final String  GENRE_TRACKS_URI    = TRACKS_URI + GENRE_OPERATION + ID_PATTERN;

    public static final String  ARTISTS_ADD_URI     = ARTISTS_URI + ADD_OPERATION;
    public static final String  GENRES_ADD_URI      = GENRES_URI + ADD_OPERATION;
    public static final String  TRACKS_ADD_URI      = TRACKS_URI + ADD_OPERATION;

    public static final String  ARTISTS_DEFAULT_URI = ARTISTS_URI + PAGE_OPERATION + PAGE_DEFAULT;
    public static final String  GENRES_DEFAULT_URI  = GENRES_URI + PAGE_OPERATION + PAGE_DEFAULT;
    public static final String  TRACKS_DEFAULT_URI  = TRACKS_URI + PAGE_OPERATION + PAGE_DEFAULT;
    
    public static final String  TRACK_ARTISTS_DEFAULT_URI   = ARTISTS_URI + TRACK_OPERATION + ID_PATTERN + PAGE_OPERATION + PAGE_DEFAULT;
    public static final String  GENRE_ARTISTS_DEFAULT_URI   = ARTISTS_URI + GENRE_OPERATION + ID_PATTERN + PAGE_OPERATION + PAGE_DEFAULT;
    public static final String  TRACK_GENRES_DEFAULT_URI    = GENRES_URI + TRACK_OPERATION + ID_PATTERN + PAGE_OPERATION + PAGE_DEFAULT;
    public static final String  ARTIST_GENRES_DEFAULT_URI   = GENRES_URI + ADD_OPERATION + ID_PATTERN + PAGE_OPERATION + PAGE_DEFAULT;
    public static final String  ARTIST_TRACKS_DEFAULT_URI   = TRACKS_URI + ARTIST_OPERATION + ID_PATTERN + PAGE_OPERATION + PAGE_DEFAULT;
    public static final String  GENRE_TRACKS_DEFAULT_URI    = TRACKS_URI + GENRE_OPERATION + ID_PATTERN + PAGE_OPERATION + PAGE_DEFAULT;

    public static final String  ARTISTS_FULL_URI    = ARTISTS_URI + PAGE_OPERATION + PAGE_PATTERN;
    public static final String  GENRES_FULL_URI     = GENRES_URI + PAGE_OPERATION + PAGE_PATTERN;
    public static final String  TRACKS_FULL_URI     = TRACKS_URI + PAGE_OPERATION + PAGE_PATTERN;
    
    public static final String  TRACK_ARTISTS_FULL_URI   = ARTISTS_URI + TRACK_OPERATION + ID_PATTERN + PAGE_OPERATION + PAGE_PATTERN;
    public static final String  GENRE_ARTISTS_FULL_URI   = ARTISTS_URI + GENRE_OPERATION + ID_PATTERN + PAGE_OPERATION + PAGE_PATTERN;
    public static final String  TRACK_GENRES_FULL_URI    = GENRES_URI + TRACK_OPERATION + ID_PATTERN + PAGE_OPERATION + PAGE_PATTERN;
    public static final String  ARTIST_GENRES_FULL_URI   = GENRES_URI + ADD_OPERATION + ID_PATTERN + PAGE_OPERATION + PAGE_PATTERN;
    public static final String  ARTIST_TRACKS_FULL_URI   = TRACKS_URI + ARTIST_OPERATION + ID_PATTERN + PAGE_OPERATION + PAGE_PATTERN;
    public static final String  GENRE_TRACKS_FULL_URI    = TRACKS_URI + GENRE_OPERATION + ID_PATTERN + PAGE_OPERATION + PAGE_PATTERN;

}
