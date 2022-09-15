package com.example.homebudget.Util;

public class AppConstant {
    //storage locations
    public static String APP_RD = "hb_room_database";
    public static String SF = "hb_shared_preferences";
    public static String APP_STORAGE = "hb_local_app_storage";
    public static String USER_NAME_SF = "user_name";
    public static String DASHBOARD_SHOW_SF = "dashboard_show";
    public static String PLANS_SHOW_SF = "plans_show";

    //warnings and alerts
    public static String SWR = "Something went wrong!";
    public static String DATA_SAVED = "Data successfully saved!";
    public static String DATA_SAVED_ERROR = "Data not saved!";
    public static String DATA_READ_ERROR = "Data not read from database!";
    public static String LOGGED_OUT = "You are logged out, please login again!";
    public static String DASHBOARD_PLAN_DISABLE = "You must enable dashboard or plans screen on start up!";

    //logs
    public static String LOG_D = "hb_log_debug: ";
    public static String LOG_E = "hb_log_error: ";

    //months
    public static class MONTHS{
        public static String
                JANUARY = "january",
                FEBRUARY = "february",
                MARCH = "march",
                APRIL = "april",
                MAY = "may",
                JUNE = "june",
                JULY = "july",
                AUGUST = "august",
                SEPTEMBER = "september",
                OCTOBER = "october",
                NOVEMBER = "november",
                DECEMBER = "december";
    }
}
