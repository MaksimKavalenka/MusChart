package by.gsu.constants;

public abstract class UriConstants {

    private static final String URI = "/muschart";

    public abstract static class Pages {

        public static final String TRACKS_PAGE_URI  = URI + "/tracks";
        public static final String ARTISTS_PAGE_URI = URI + "/artists";
        public static final String GENRES_PAGE_URI  = URI + "/genres";

    }

    public abstract static class Forms {

        public static final String LOGIN_FORM_URI               = URI + "/login";
        public static final String REGISTRATION_FORM_URI        = URI + "/registration";
        public static final String ADD_TRACK_FORM_URI           = URI + "/track/add";
        public static final String DELETE_TRACK_FORM_URI        = URI + "/track/delete";
        public static final String ADD_ARTIST_FORM_URI          = URI + "/artist/add";
        public static final String DELETE_ARTIST_FORM_URI       = URI + "/artist/delete";
        public static final String ADD_GENRE_FORM_URI           = URI + "/genre/add";
        public static final String DELETE_GENRE_FORM_URI        = URI + "/genre/delete";
        public static final String ADD_TRACK_ARTIST_FORM_URI    = URI + "/track_artists/add";
        public static final String DELETE_TRACK_ARTIST_FORM_URI = URI + "/track_artists/delete";
        public static final String ADD_TRACK_GENRE_FORM_URI     = URI + "/track_genres/add";
        public static final String DELETE_TRACK_GENRE_FORM_URI  = URI + "/track_genres/delete";
        public static final String ADD_ARTIST_GENRE_FORM_URI    = URI + "/artist_genres/add";
        public static final String DELETE_ARTIST_GENRE_FORM_URI = URI + "/artist_genres/delete";

    }

}
