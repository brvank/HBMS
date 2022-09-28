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
    public static String OOPS = "Oops!";
    public static String OK = "OK";
    public static String CANCEL = "CANCEL";
    public static String CHOOSE = "Choose";
    public static String SWW = "Something went wrong!";
    public static String DATA_SAVED = "Data successfully saved!";
    public static String DATA_SAVED_ERROR = "Data not saved!";
    public static String DATA_READ_ERROR = "Data not read from database!";
    public static String LOGGED_OUT = "You are logged out, please login again!";
    public static String DASHBOARD_PLAN_DISABLE = "You must enable dashboard or plans screen on start up!";
        //text change
    public static int NAME_MAX_LENGTH = 20;
    public static int INFO_MAX_LENGTH = 40;

    public static String ZERO_LENGTH_STRING = "";

    public static String CATEGORY_NAME_REQUIRED = "Category name is required!";
    public static String CATEGORY_INFO_REQUIRED = "Category info is required!";
    public static String CATEGORY_NAME_LENGTH_CROSSED = "Category name must have 20 characters only!";
    public static String CATEGORY_INFO_LENGTH_CROSSED = "Category info must have 40 characters only!";

    public static String PLAN_NAME_REQUIRED = "Plan name is required!";
    public static String PLAN_INFO_REQUIRED = "Plan info is required!";
    public static String PLAN_NAME_LENGTH_CROSSED = "Plan name must have 20 characters only!";
    public static String PLAN_INFO_LENGTH_CROSSED = "Plan info must have 40 characters only!";

    public static String ITEM_NAME_REQUIRED = "Item name is required!";
    public static String ITEM_INFO_REQUIRED = "Item info is required!";
    public static String ITEM_NAME_LENGTH_CROSSED = "Item name must have 20 characters only!";
    public static String ITEM_INFO_LENGTH_CROSSED = "Item info must have 40 characters only!";


    //tags for dialogs
    public static String SELECTION_DIALOG_TAG = "selection_dialog_tag";
    public static String CATEGORY_DIALOG_TAG = "category_dialog_tag";
    public static String PLAN_DIALOG_TAG = "plan_dialog_tag";
    public static String ITEM_DIALOG_TAG = "item_dialog_tag";
    public static String MESSAGE_DIALOG_TAG = "message_dialog_tag";

    //data
    public static String CATEGORY = "Category";
    public static String PLAN = "Plan";
    public static String ITEM = "Item";
    public static String DASHBOARD = "Dashboard";

    //logs
    public static String LOG_D = "hb_log_debug: ";
    public static String LOG_E = "hb_log_error: ";

    //dimensions
    public static int CARD_WIDTH = 128;

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
