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

    // 新レイアウトのtask_tableテーブルを作成
    public static final String MIGRATION_2_3_CREATE_new_task_table =
            "CREATE TABLE new_task_table (" +
                    "    tskId TEXT NOT NULL," +
                    "    tskNm TEXT," +
                    "    tskDtl TEXT," +
                    "    tskExecFrcyId TEXT," +
                    "    prtyId TEXT," +
                    "    tskCompDttm TEXT," +
                    "    reviewCgryId TEXT," +
                    "    reviewComment TEXT," +
                    "    rstrDttm TEXT," +
                    "    updtDttm TEXT," +
                    "    PRIMARY KEY(tskId)" +
                    ");";
    // 必要なカラムだけコピー
    public static final String MIGRATION_2_3_COPY_DATA_task_table_TO_new_task_table =
            "INSERT INTO new_task_table (" +
                    "tskId, tskNm, tskDtl, tskExecFrcyId, prtyId, tskCompDttm, rstrDttm, updtDttm " +
                    ") " +
                    "SELECT " +
                    "tskId, tskNm, tskDtl, tskExecFrcyId, prtyId, tskCompDttm, rstrDttm, updtDttm " +
                    "FROM task_table;";
    // 旧レイアウトのテーブルを削除
    public static final String MIGRATION_2_3_DROP_TABLE_task_table =
            "DROP TABLE task_table;";
    // 新レイアウトのテーブルをリネーム
    public static final String MIGRATION_2_3_RENAME_new_task_table_TO_task_table =
            "ALTER TABLE new_task_table RENAME TO task_table;";
}
