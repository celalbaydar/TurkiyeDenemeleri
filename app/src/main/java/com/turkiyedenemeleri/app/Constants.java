package com.turkiyedenemeleri.app;

/**
 * Created by celal on 23/04/2017.
 */

import java.io.File;

/**
 * Created by codeest on 2016/8/3.
 */
public class Constants {
    public interface ACTION {
        public static String MAIN_ACTION = "com.turkiyedenemeleri.chronometer.action.main";
        public static String STARTFOREGROUND_ACTION = "com.turkiyedenemeleri.chronometer.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.turkiyedenemeleri.chronometer.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }

    public static final String PATH_DATA = MyApp.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";


    public static final int clickEventCode = 1000;
    public static final int showkarala = 1001;
    public static final int hidekarala = 1002;
    public static final int sınavaBasla = 1003;

}

