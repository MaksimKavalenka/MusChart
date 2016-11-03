package com.muschart.constants;

public abstract class UrlConstants {

    public static final String ANY = "/**";

    public static abstract class Page {

        public static abstract class Common {

            public static final String LOGIN_URL        = "/login";
            public static final String REGISTRATION_URL = "/register";
            public static final String SETTINGS_URL     = "/settings";
            public static final String PLAYLIST_URL     = "/playlist";

        }

        public static abstract class Artist {

            public static final String ARTISTS_URL       = "/artists";
            public static final String ARTIST_URL        = "/artist";
            public static final String ARTIST_ADD_URL    = ARTIST_URL + Operation.ADD_OPERATION;
            public static final String ARTIST_FULL_URL   = ARTIST_URL + Key.ID_KEY;
            public static final String ARTIST_GENRES_URL = ARTIST_URL + Key.ID_KEY
                    + Genre.GENRES_URL;
            public static final String ARTIST_TRACKS_URL = ARTIST_URL + Key.ID_KEY
                    + Track.TRACKS_URL;

        }

        public static abstract class Genre {

            public static final String GENRES_URL        = "/genres";
            public static final String GENRE_URL         = "/genre";
            public static final String GENRE_ADD_URL     = GENRE_URL + Operation.ADD_OPERATION;
            public static final String GENRE_FULL_URL    = GENRE_URL + Key.ID_KEY;
            public static final String GENRE_ARTISTS_URL = GENRE_URL + Key.ID_KEY
                    + Artist.ARTISTS_URL;
            public static final String GENRE_TRACKS_URL  = GENRE_URL + Key.ID_KEY
                    + Track.TRACKS_URL;

        }

        public static abstract class Track {

            public static final String TRACKS_URL        = "/tracks";
            public static final String TRACK_URL         = "/track";
            public static final String TRACK_ADD_URL     = TRACK_URL + Operation.ADD_OPERATION;
            public static final String TRACK_FULL_URL    = TRACK_URL + Key.ID_KEY;
            public static final String TRACK_ARTISTS_URL = TRACK_URL + Key.ID_KEY
                    + Artist.ARTISTS_URL;
            public static final String TRACK_GENRES_URL  = TRACK_URL + Key.ID_KEY
                    + Genre.GENRES_URL;

        }

        public static abstract class User {

            public static final String USER_URL         = "/user";
            public static final String USER_ARTISTS_URL = USER_URL + Artist.ARTISTS_URL;
            public static final String USER_GENRES_URL  = USER_URL + Genre.GENRES_URL;
            public static final String USER_TRACKS_URL  = USER_URL + Track.TRACKS_URL;

        }

        public static abstract class Key {

            public static final String ID_KEY   = "/{id:[0-9]{1,}}";
            public static final String PAGE_KEY = "/{page:[0-9]{1,}}";

        }

        public static abstract class Operation {

            public static final String ADD_OPERATION = "/add";

        }

    }

    public static abstract class Rest {

        public static final String JSON_EXT   = ".json";

        public static final String ARTIST_URL = "/artist";
        public static final String GENRE_URL  = "/genre";
        public static final String TRACK_URL  = "/track";
        public static final String UNIT_URL   = "/unit";
        public static final String UPLOAD_URL = "/upload";
        public static final String USER_URL   = "/user";

        public static abstract class Operation {

            public static final String AUTH_OPERATION   = "/auth";
            public static final String CHECK_OPERATION  = "/check";
            public static final String CREATE_OPERATION = "/create";
            public static final String DELETE_OPERATION = "/delete";
            public static final String GET_OPERATION    = "/get";
            public static final String LIKE_OPERATION   = "/like";
            public static final String LOGOUT_OPERATION = "/logout";
            public static final String UPDATE_OPERATION = "/update";
            public static final String USER_OPERATION   = "/user";

        }

    }

}
