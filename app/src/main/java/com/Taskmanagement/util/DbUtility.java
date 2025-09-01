package com.Taskmanagement.util;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DbUtility {


    // 優先度
    public static String PRIORITY_LOW_ID = "1";
    public static String PRIORITY_MIDDLE_ID = "2";
    public static String PRIORITY_HIGH_ID = "3";
    public static String PRIORITY_LOW_JP = "低";
    public static String PRIORITY_MIDDLE_JP = "中";
    public static String PRIORITY_HIGH_JP = "高";
    public static Map<String, String> priorityMap = new HashMap(){{
        put(PRIORITY_LOW_ID, PRIORITY_LOW_JP);
        put(PRIORITY_MIDDLE_ID, PRIORITY_MIDDLE_JP);
        put(PRIORITY_HIGH_ID, PRIORITY_HIGH_JP);
    }};

    public static LocalDateTime getLocalDateTime(String date, String time) {
        return null;
    }
}