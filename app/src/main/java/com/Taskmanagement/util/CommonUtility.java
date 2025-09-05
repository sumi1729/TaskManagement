package com.Taskmanagement.util;

public class CommonUtility {

    public static final String FIRST_LOOP = "firstLoop";
    public static final String OTHER = "その他";

    public static boolean isNullOrEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }
}
