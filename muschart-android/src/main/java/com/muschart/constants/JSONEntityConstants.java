package com.muschart.constants;

public abstract class JSONEntityConstants {

    private abstract static class JSONAbstractFields {

        private static final String ID = "id";

    }

    public abstract static class JSONArtistFields {

        public static final String ID = JSONAbstractFields.ID;
        public static final String NAME = "name";
        public static final String PHOTO = "photo";
        public static final String RATING = "rating";
        public static final String LIKED = "liked";

    }

    public abstract static class JSONGenreFields {

        public static final String ID = JSONAbstractFields.ID;
        public static final String NAME = "name";
        public static final String RATING = "rating";
        public static final String LIKED = "liked";

    }

    public abstract static class JSONTrackFields {

        public static final String ID = JSONAbstractFields.ID;
        public static final String NAME = "name";
        public static final String SONG = "song";
        public static final String COVER = "cover";
        public static final String VIDEO = "video";
        public static final String RELEASE = "release";
        public static final String RATING = "rating";
        public static final String LIKED = "liked";
        public static final String ARTISTS = "artists";
        public static final String UNITS = "units";

    }

    public abstract static class JSONUnitFields {

        public static final String ID   = JSONAbstractFields.ID;
        public static final String NAME = "name";

    }

    public abstract static class JSONUserFields {

        public static final String LOGIN = "login";
        public static final String ADMIN = "admin";

    }

}