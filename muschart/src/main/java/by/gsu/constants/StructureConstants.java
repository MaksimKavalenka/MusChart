package by.gsu.constants;

public abstract class StructureConstants {

    public abstract static class Tables {

        public static final String TRACK        = "track";
        public static final String ARTIST       = "artist";
        public static final String GENRE        = "genre";
        public static final String USER         = "user";
        public static final String TRACK_ARTIST = "track_artist";
        public static final String TRACK_GENRE  = "track_genre";
        public static final String ARTIST_GENRE = "artist_genre";
        public static final String USER_TRACK   = "user_track";
        public static final String USER_ARTIST  = "user_artist";
        public static final String USER_GENRE   = "user_genre";

    }

    public abstract static class TrackColumns {

        public static final String ID        = "id";
        public static final String SONG_NAME = "song_name";
        public static final String SONG      = "song";
        public static final String COVER     = "cover";
        public static final String CAST_NAME = "cast_name";
        public static final String DATE      = "date";
        public static final String RATING    = "rating";

    }

    public abstract static class ArtistColumns {

        public static final String ID     = "id";
        public static final String NAME   = "name";
        public static final String PHOTO  = "photo";
        public static final String RATING = "rating";

    }

    public abstract static class GenreColumns {

        public static final String ID     = "id";
        public static final String NAME   = "name";
        public static final String RATING = "rating";

    }

    public abstract static class UserColumns {

        public static final String ID       = "id";
        public static final String LOGIN    = "login";
        public static final String PASSWORD = "password";
        public static final String ROLE     = "role";

    }

    public abstract static class RelationColumns {

        public static final String ID_TRACK  = "id_track";
        public static final String ID_ARTIST = "id_artist";
        public static final String ID_GENRE  = "id_genre";
        public static final String ID_USER   = "id_user";

    }

}
