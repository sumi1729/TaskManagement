package com.Taskmanagement.util;

import java.time.format.DateTimeFormatter;

public class CommonUtility {

    // 日時変換フォーマット
    public static final DateTimeFormatter DATE_TIME_FORMATTER_YYYY_M_DD_HH_MM = DateTimeFormatter.ofPattern("yyyy-M-dd hh:mm");
    public static final DateTimeFormatter DATE_TIME_FORMATTER_YYYY_M_DD = DateTimeFormatter.ofPattern("yyyy-M-dd");
    public static final DateTimeFormatter DATE_TIME_FORMATTER_YY_MM_DD = DateTimeFormatter.ofPattern("yy-MM-dd");
    public static final DateTimeFormatter DATE_TIME_FORMATTER_HH_MM = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter DATE_TIME_FORMATTER_ISO_LOCAL_DATE_TIME = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public static final DateTimeFormatter DATE_TIME_FORMATTER_ISO_LOCAL_DATE = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter DATE_TIME_FORMATTER_ISO_LOCAL_TIME = DateTimeFormatter.ISO_LOCAL_TIME;

    // 画面ID
    public enum ScreenId {
        ALL_TASK,
        SCHEDULE
    }

    // その他一般
    public static final String TAG = "TaskManagement";
    public static final String FIRST_LOOP = "firstLoop";
    public static final String OTHER = "その他";
    public static final String DATE_TIME_MITEI = "日時未定";
    public static final String TIME_MITEI = "時刻未定";
    public static final String DELIMITER_HYPHON = "-";

    public static boolean isNullOrEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

    public static String getStrDate(int year, int month, int dayOfMonth, String delimiter, boolean needMonthAdjust) {
        String tmpStrYear = String.valueOf(year);
        int tmpMonth = needMonthAdjust ? month + 1 : month;
        String strYear = year < 10 ? "" : tmpStrYear.substring(tmpStrYear.length() - 2, tmpStrYear.length());
        String strMonth = tmpMonth < 10 ? "0" + String.valueOf(tmpMonth) : String.valueOf(tmpMonth);
        String strDayOfMonth = dayOfMonth < 10 ? "0" + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
        return strYear + delimiter + strMonth + delimiter +strDayOfMonth;
    }

}
