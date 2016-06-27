package by.gsu.constants;

public abstract class PathConstants {

    private static final String PATH = "/WEB-INF/jsp";
    private static final String EXT  = ".jsp";

    public abstract static class Pages {

        private static final String LOCAL_PATH       = "/page";
        private static final String FULL_PATH        = PATH + LOCAL_PATH;

        public static final String  TRACK_PAGE_PATH  = FULL_PATH + "/trackPage" + EXT;
        public static final String  ARTIST_PAGE_PATH = FULL_PATH + "/artistPage" + EXT;
        public static final String  GENRE_PAGE_PATH  = FULL_PATH + "/genrePage" + EXT;

    }

    public abstract static class Forms {

        private static final String LOCAL_PATH                    = "/form";
        private static final String FULL_PATH                     = PATH + LOCAL_PATH;

        public static final String  LOGIN_FORM_PATH               = FULL_PATH + "/loginForm" + EXT;
        public static final String  REGISTRATION_FORM_PATH        = FULL_PATH + "/registrationForm"
                + EXT;
        public static final String  ADD_TRACK_FORM_PATH           = FULL_PATH + "/addTrackForm"
                + EXT;
        public static final String  ADD_ARTIST_FORM_PATH          = FULL_PATH + "/addArtistForm"
                + EXT;
        public static final String  ADD_GENRE_FORM_PATH           = FULL_PATH + "/addGenreForm"
                + EXT;
        public static final String  ADD_TRACK_ARTIST_FORM_PATH    = FULL_PATH
                + "/addTrackArtistsForm" + EXT;
        public static final String  ADD_TRACK_GENRE_FORM_PATH     = FULL_PATH
                + "/addTrackGenresForm" + EXT;
        public static final String  ADD_ARTIST_GENRE_FORM_PATH    = FULL_PATH
                + "/addArtistGenresForm" + EXT;
        public static final String  DELETE_TRACK_FORM_PATH        = FULL_PATH + "/deleteTrackForm"
                + EXT;
        public static final String  DELETE_ARTIST_FORM_PATH       = FULL_PATH + "/deleteArtistForm"
                + EXT;
        public static final String  DELETE_GENRE_FORM_PATH        = FULL_PATH + "/deleteGenreForm"
                + EXT;
        public static final String  DELETE_TRACK_ARTIST_FORM_PATH = FULL_PATH
                + "/deleteTrackArtistsForm" + EXT;
        public static final String  DELETE_TRACK_GENRE_FORM_PATH  = FULL_PATH
                + "/deleteTrackGenresForm" + EXT;
        public static final String  DELETE_ARTIST_GENRE_FORM_PATH = FULL_PATH
                + "/deleteArtistGenresForm" + EXT;

    }

}
