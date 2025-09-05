package com.Taskmanagement.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.Taskmanagement.model.TaskEntity;

import java.time.LocalDateTime;
import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(TaskEntity task);

    @Query("SELECT * FROM task_table WHERE taskCompleteDatetime is null ORDER BY priorityId DESC")
    LiveData<List<TaskEntity>> getAllIncompTask();

    @Query("UPDATE task_table SET taskCompleteDatetime = :taskCompleteDatetime WHERE taskId = :taskId")
    void updtTskEntyTskCompDttm(String taskId, LocalDateTime taskCompleteDatetime);

}
