package by.gsu.constants;

public abstract class PageConstants {

    private static final String LOGIN        = "/login";
    private static final String MAIN         = "/main";
    private static final String REGIGTRATION = "/registration";

    public static abstract class PathConstants {

        private static final String PATH              = "/page";

        public static final String  LOGIN_PATH        = PATH + LOGIN;
        public static final String  MAIN_PATH         = PATH + MAIN;
        public static final String  REGIGTRATION_PATH = PATH + REGIGTRATION;

    }

    public static abstract class UriConstants {

        private static final String URI              = "redirect:";

        public static final String  LOGIN_URI        = URI + LOGIN;
        public static final String  MAIN_URI         = URI + MAIN;
        public static final String  REGIGTRATION_URI = URI + REGIGTRATION;

    }

}
