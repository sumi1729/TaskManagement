package com.Taskmanagement.util;

import java.util.HashMap;
import java.util.Map;

public class DbUtility {

    // 優先度
    public static final String PRIORITY_LOW_ID = "1";
    public static final String PRIORITY_MIDDLE_ID = "2";
    public static final String PRIORITY_HIGH_ID = "3";
    public static final String PRIORITY_LOW_JP = "低";
    public static final String PRIORITY_MIDDLE_JP = "中";
    public static final String PRIORITY_HIGH_JP = "高";
    public static final Map<String, String> priorityMap = new HashMap<String, String>(){{
        put(PRIORITY_LOW_ID, PRIORITY_LOW_JP);
        put(PRIORITY_MIDDLE_ID, PRIORITY_MIDDLE_JP);
        put(PRIORITY_HIGH_ID, PRIORITY_HIGH_JP);
    }};

    // スケジュールテーブル
    // スケジュール状態
    public enum SCDL_STAT {
        NOT_DONE, DONE, LOGC_DEL
    }
}