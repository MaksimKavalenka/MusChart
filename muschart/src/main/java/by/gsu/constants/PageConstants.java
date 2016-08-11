package by.gsu.constants;

public abstract class PageConstants {

    public static final String  REDIRECT            = "redirect:";
    public static final String  INDEX_PATH          = "/index";

    private static final String ADD_OPERATION       = "/add";
    private static final String PAGE_OPERATION      = "/page";

    private static final String DEFAULT_PAGE        = PAGE_OPERATION + "/1";
    private static final String PATTERN_PAGE        = PAGE_OPERATION + "/{page:[0-9]{1,}}";

    public static final String  SETTINGS_URI        = "/settings";
    public static final String  LOGIN_URI           = "/login";
    public static final String  REGISTRATION_URI    = "/register";
    public static final String  ARTISTS_URI         = "/artists";
    public static final String  GENRES_URI          = "/genres";
    public static final String  TRACKS_URI          = "/tracks";
    public static final String  ARTISTS_ADD_URI     = ARTISTS_URI + ADD_OPERATION;
    public static final String  GENRES_ADD_URI      = GENRES_URI + ADD_OPERATION;
    public static final String  TRACKS_ADD_URI      = TRACKS_URI + ADD_OPERATION;

    public static final String  ARTISTS_DEFAULT_URI = ARTISTS_URI + DEFAULT_PAGE;
    public static final String  GENRES_DEFAULT_URI  = GENRES_URI + DEFAULT_PAGE;
    public static final String  TRACKS_DEFAULT_URI  = TRACKS_URI + DEFAULT_PAGE;

    public static final String  ARTISTS_FULL_URI    = ARTISTS_URI + PATTERN_PAGE;
    public static final String  GENRES_FULL_URI     = GENRES_URI + PATTERN_PAGE;
    public static final String  TRACKS_FULL_URI     = TRACKS_URI + PATTERN_PAGE;

}
