package com.muschart.constants;

public abstract class UploadConstants {

    public abstract static class Path {

        private static final String ROOT                     = "/ServerData/muschart";
        private static final String IMAGE_ROOT               = ROOT + "/image";

        public static final String  ARTIST_PHOTO_UPLOAD_PATH = IMAGE_ROOT + "/artist";
        public static final String  AUDIO_UPLOAD_PATH        = ROOT + "/audio";
        public static final String  TRACK_COVER_UPLOAD_PATH  = IMAGE_ROOT + "/track";

    }

    public abstract static class Type {

        public static final String COVER = "cover";
        public static final String PHOTO = "photo";
        public static final String SONG  = "song";

    }

}
