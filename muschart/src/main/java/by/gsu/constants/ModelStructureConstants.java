package by.gsu.constants;

public abstract class ModelStructureConstants {

    public abstract static class Entities {

        public static final String ARTIST = "artist";
        public static final String GENRE  = "genre";
        public static final String ROLE   = "role";
        public static final String TRACK  = "track";
        public static final String UNIT   = "unit";
        public static final String USER   = "user";

    }

    public abstract static class AbstractFields {

        public static final String ID = "id";

    }

    public abstract static class ArtistFields {

        public static final String ID     = AbstractFields.ID;
        public static final String NAME   = "name";
        public static final String PHOTO  = "photo";
        public static final String RATING = "rating";

    }

    public abstract static class GenreFields {

        public static final String ID     = AbstractFields.ID;
        public static final String NAME   = "name";
        public static final String RATING = "rating";

    }

    public abstract static class RoleFields {

        public static final String ID   = AbstractFields.ID;
        public static final String NAME = "name";

    }

    public abstract static class TrackFields {

        public static final String ID      = AbstractFields.ID;
        public static final String NAME    = "name";
        public static final String SONG    = "song";
        public static final String COVER   = "cover";
        public static final String VIDEO   = "video";
        public static final String RELEASE = "release";
        public static final String RATING  = "rating";

    }

    public abstract static class UnitFields {

        public static final String ID   = AbstractFields.ID;
        public static final String NAME = "name";

    }

    public abstract static class UserFields {

        public static final String ID       = AbstractFields.ID;
        public static final String LOGIN    = "login";
        public static final String PASSWORD = "password";

    }

    public abstract static class RelationFields {

        public static final String ARTISTS = "artists";
        public static final String GENRES  = "genres";
        public static final String TRACKS  = "tracks";
        public static final String UNITS   = "units";
        public static final String USERS   = "users";

    }

}
