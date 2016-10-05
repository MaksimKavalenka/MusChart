package by.gsu.constants;

import org.springframework.data.domain.Sort.Direction;

public abstract class EntityConstants {

    public static abstract class ElementsCount {

        public static abstract class ArtistCountElements {

            public static final int ARTIST_COUNT_ELEMENTS      = 6;
            public static final int ARTIST_FULL_COUNT_ELEMENTS = 18;

        }

        public static abstract class GenreCountElements {

            public static final int GENRE_COUNT_ELEMENTS      = 18;
            public static final int GENRE_FULL_COUNT_ELEMENTS = 54;

        }

        public static abstract class TrackCountElements {

            public static final int TRACK_COUNT_ELEMENTS      = 6;
            public static final int TRACK_FULL_COUNT_ELEMENTS = 18;

        }

    }

    public static abstract class Sort {

        public static abstract class Asc {

            private static final Direction DIRECTION = Direction.ASC;

            public static abstract class Artist {

                public static final org.springframework.data.domain.Sort ID     = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.ArtistFields.ID);

                public static final org.springframework.data.domain.Sort NAME   = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.ArtistFields.NAME);

                public static final org.springframework.data.domain.Sort RATING = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.ArtistFields.RATING);

            }

            public static abstract class Genre {

                public static final org.springframework.data.domain.Sort ID     = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.GenreFields.ID);

                public static final org.springframework.data.domain.Sort NAME   = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.GenreFields.NAME);

                public static final org.springframework.data.domain.Sort RATING = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.GenreFields.RATING);

            }

            public static abstract class Track {

                public static final org.springframework.data.domain.Sort ID      = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.TrackFields.ID);

                public static final org.springframework.data.domain.Sort NAME    = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.TrackFields.NAME);

                public static final org.springframework.data.domain.Sort RATING  = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.TrackFields.RATING);

                public static final org.springframework.data.domain.Sort RELEASE = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.TrackFields.RELEASE);

            }

        }

        public static abstract class Desc {

            private static final Direction DIRECTION = Direction.DESC;

            public static abstract class Artist {

                public static final org.springframework.data.domain.Sort ID     = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.ArtistFields.ID);

                public static final org.springframework.data.domain.Sort NAME   = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.ArtistFields.NAME);

                public static final org.springframework.data.domain.Sort RATING = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.ArtistFields.RATING);

            }

            public static abstract class Genre {

                public static final org.springframework.data.domain.Sort ID     = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.GenreFields.ID);

                public static final org.springframework.data.domain.Sort NAME   = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.GenreFields.NAME);

                public static final org.springframework.data.domain.Sort RATING = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.GenreFields.RATING);

            }

            public static abstract class Track {

                public static final org.springframework.data.domain.Sort ID      = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.TrackFields.ID);

                public static final org.springframework.data.domain.Sort NAME    = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.TrackFields.NAME);

                public static final org.springframework.data.domain.Sort RATING  = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.TrackFields.RATING);

                public static final org.springframework.data.domain.Sort RELEASE = new org.springframework.data.domain.Sort(
                        DIRECTION, Structure.TrackFields.RELEASE);

            }

        }

    }

    public static abstract class Structure {

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

}
