package com.muschart.constants;

public abstract class SolrConstants {

    public static abstract class Core {

        private static final String SOLR_URL         = "http://localhost:8983/solr";
        private static final String PREFIX           = "/muschart_";

        public static final String  ARTISTS_CORE_URI = SOLR_URL + PREFIX + "artists";
        public static final String  GENRES_CORE_URI  = SOLR_URL + PREFIX + "genres";
        public static final String  TRACKS_CORE_URI  = SOLR_URL + PREFIX + "tracks";

    }

    public static abstract class Fields {

        public static abstract class ArtistsFields {

            public static final String ID   = "id";
            public static final String NAME = "name";

        }

        public static abstract class GenresFields {

            public static final String ID   = "id";
            public static final String NAME = "name";

        }

        public static abstract class TracksFields {

            public static final String ID   = "id";
            public static final String NAME = "name";

        }

    }

}