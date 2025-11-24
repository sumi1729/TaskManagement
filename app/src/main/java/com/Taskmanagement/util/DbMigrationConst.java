package com.Taskmanagement.util;

public class DbMigrationConst {
    // MIGRATION_1_2
    public static final String MIGRATION_1_2_CREATE_kpt_table =
            "CREATE TABLE kpt_table (" +
                    "    kptId TEXT NOT NULL," +
                    "    kptNm TEXT," +
                    "    kptDtl TEXT," +
                    "    compDt TEXT," +
                    "    rstrDttm TEXT," +
                    "    updtDttm TEXT," +
                    "    PRIMARY KEY (kptId)" +
                    ");";
    public static final String MIGRATION_1_2_CREATE_kpt_history_table =
            "CREATE TABLE kpt_history_table (" +
                    "    kptId TEXT NOT NULL," +
                    "    sralNum INTEGER NOT NULL," +
                    "    kptType TEXT," +
                    "    compDt TEXT," +
                    "    rstrDttm TEXT," +
                    "    updtDttm TEXT," +
                    "    PRIMARY KEY (kptId, sralNum)" +
                    ");";
    public static final String MIGRATION_1_2_CREATE_kpt_link_table =
            "CREATE TABLE kpt_link_table (" +
                    "    kptId_P TEXT NOT NULL," +
                    "    kptId_Kt TEXT NOT NULL," +
                    "    rstrDttm TEXT," +
                    "    updtDttm TEXT," +
                    "    PRIMARY KEY (kptId_P, kptId_Kt)" +
                    ");";
    public static final String MIGRATION_1_2_CREATE_tag_table =
            "CREATE TABLE tag_table (" +
                    "    tskKptId TEXT NOT NULL," +
                    "    tagId TEXT NOT NULL," +
                    "    rstrDttm TEXT," +
                    "    updtDttm TEXT," +
                    "    PRIMARY KEY (tskKptId, tagId)" +
                    ");";
}
