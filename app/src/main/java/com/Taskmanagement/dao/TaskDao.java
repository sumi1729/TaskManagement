package com.Taskmanagement.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.Taskmanagement.entity.TskEntity;

import java.time.LocalDateTime;
import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(TskEntity task);

    @Query("SELECT * FROM task_table WHERE tskCompDttm is null ORDER BY prtyId DESC")
    LiveData<List<TskEntity>> getAllIncompTask();

    @Query("UPDATE task_table SET tskCompDttm = :tskCompDttm WHERE tskId = :tskId")
    void updtTskEntyTskCompDttm(String tskId, LocalDateTime tskCompDttm);

}
