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

    // その他一般
    public static final String TAG = "TaskManagement";
    public static final String FIRST_LOOP = "firstLoop";
    public static final String OTHER = "その他";
    public static final String DATE_TIME_MITEI = "日時未定";
    public static final String TIME_MITEI = "時刻未定";

    public static boolean isNullOrEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

}
