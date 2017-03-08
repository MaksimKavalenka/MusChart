package com.muschart.constants;

public abstract class UrlConstants {

    private static final String BASE_URL = "http://903757dc.ngrok.io";

    public abstract class MetadataUrlConstants {

        private static final String IMAGE_METADATA = "/image";

        public static final String AUDIO_METADATA = BASE_URL + "/audio";
        public static final String ARTIST_IMAGE_METADATA = BASE_URL + IMAGE_METADATA + "/artist";
        public static final String TRACK_IMAGE_METADATA = BASE_URL + IMAGE_METADATA + "/track";

    }

    public abstract class ServiceUrlConstants {

        private static final String SERVICE_URL = "/service";

        public static final String ARTIST_SERVICE = BASE_URL + SERVICE_URL + "/artists";
        public static final String GENRE_SERVICE = BASE_URL + SERVICE_URL + "/genres";
        public static final String TRACK_SERVICE = BASE_URL + SERVICE_URL + "/tracks";
        public static final String USER_SERVICE = BASE_URL + SERVICE_URL + "/users";

    }

}