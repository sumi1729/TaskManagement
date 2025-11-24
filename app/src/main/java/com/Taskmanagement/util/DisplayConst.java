package com.Taskmanagement.util;

import java.util.List;

public class DisplayConst {
    // タスク画面・スケジュール画面
    public static final String DATE_TIME_MITEI = "日時未定";
    public static final String TIME_MITEI = "時刻未定";

    // タスク登録画面
    // 入力フォーマットスピナー用
    public static final String INPUT_FORMAT_SPINNER_TASK = "タスク";
    public static final String INPUT_FORMAT_SPINNER_KPT = "KPT";
    // KPTスピナー用
    public static final String KPT_TYPE_SPINNER_KEEP = "Keep";
    public static final String KPT_TYPE_SPINNER_PROBLEM = "Problem";
    public static final String KPT_TYPE_SPINNER_TRY = "Try";
    // 優先度スピナー用
    public static final String PRIORITY_SPINNER_NON_SETTING = "設定なし";
    public static final String PRIORITY_SPINNER_LOW = "低";
    public static final String PRIORITY_SPINNER_MID = "中";
    public static final String PRIORITY_SPINNER_HIGH = "高";
    // タグスピナー用
    public static final String TAG_SPINNER_JOB = "仕事";
    public static final String TAG_SPINNER_CHANGE_JOB = "転職";
    public static final String TAG_SPINNER_PRIVATE = "私生活";

    public static final List<String> INPUT_FORMAT_LIST = List.of(
            INPUT_FORMAT_SPINNER_TASK,
            INPUT_FORMAT_SPINNER_KPT
    );
    public static final List<String> KPT_TYPE_LIST = List.of(
            KPT_TYPE_SPINNER_KEEP,
            KPT_TYPE_SPINNER_PROBLEM,
            KPT_TYPE_SPINNER_TRY
    );
    public static final List<String> PRIORITY_LIST = List.of(
            PRIORITY_SPINNER_NON_SETTING,
            PRIORITY_SPINNER_LOW,
            PRIORITY_SPINNER_MID,
            PRIORITY_SPINNER_HIGH
    );
    public static final List<String> TAG_LIST = List.of(
            TAG_SPINNER_JOB,
            TAG_SPINNER_CHANGE_JOB,
            TAG_SPINNER_PRIVATE
    );

    // その他一般
    public static final String DELIMITER_HYPHON = "-";
}
