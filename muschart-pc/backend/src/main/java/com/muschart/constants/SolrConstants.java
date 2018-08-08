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

        public static abstract class SolrFields {

            public static final String SHARD = "[shard]";

        }

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

    public static abstract class Key {

        public static final String DOCS     = "docs";
        public static final String RESPONSE = "response";

    }

    public static abstract class Query {

        public static final String FACET              = "facet";
        public static final String FACET_RANGE        = FACET + ".range";
        public static final String FACET_RANGE_END    = FACET_RANGE + ".end";
        public static final String FACET_RANGE_GAP    = FACET_RANGE + ".gap";
        public static final String FACET_RANGE_START  = FACET_RANGE + ".start";
        public static final String FACETED_FIELDS     = FACET + ".field";
        public static final String FIELD_LIST         = "fl";
        public static final String FILTER_QUERY       = "fq";
        public static final String HIGHLIGHT          = "hl";
        public static final String HIGHLIGHTED_FIELDS = HIGHLIGHT + ".fl";
        public static final String ROWS               = "rows";
        public static final String SHARDS             = "shards";
        public static final String SORTING            = "sort";
        public static final String START              = "start";
        public static final String QUERY              = "q";
        public static final String WRITER_TYPE        = "wt";

    }

}