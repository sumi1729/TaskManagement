package com.Taskmanagement.database;

import static com.Taskmanagement.util.DbMigrationConst.MIGRATION_1_2_CREATE_kpt_history_table;
import static com.Taskmanagement.util.DbMigrationConst.MIGRATION_1_2_CREATE_kpt_link_table;
import static com.Taskmanagement.util.DbMigrationConst.MIGRATION_1_2_CREATE_kpt_table;
import static com.Taskmanagement.util.DbMigrationConst.MIGRATION_1_2_CREATE_tag_table;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.Taskmanagement.dao.KptDao;
import com.Taskmanagement.dao.TaskDao;
import com.Taskmanagement.entity.KptEntity;
import com.Taskmanagement.entity.KptHstryEntity;
import com.Taskmanagement.entity.KptLinkEntity;
import com.Taskmanagement.entity.ScdlEntity;
import com.Taskmanagement.entity.TagEntity;
import com.Taskmanagement.entity.TskEntity;

@Database(
        entities = {TskEntity.class, ScdlEntity.class, KptEntity.class, KptHstryEntity.class, KptLinkEntity.class, TagEntity.class},
        version = 2,
        exportSchema = true)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    public abstract KptDao kptDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(MIGRATION_1_2_CREATE_kpt_table);
            database.execSQL(MIGRATION_1_2_CREATE_kpt_history_table);
            database.execSQL(MIGRATION_1_2_CREATE_kpt_link_table);
            database.execSQL(MIGRATION_1_2_CREATE_tag_table);
        }
    };
}
