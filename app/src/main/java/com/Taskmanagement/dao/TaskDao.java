package com.Taskmanagement.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.Taskmanagement.model.TaskEntity;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(TaskEntity task);

    @Query("SELECT * FROM task_table")
    List<TaskEntity> getAllTask();

    @Query("SELECT * FROM task_table")
    LiveData<List<TaskEntity>> getAllTaskLive();
    @Query("SELECT count(*) FROM task_table")
    int getAllTaskLive_();
    @Query("SELECT * FROM task_table WHERE taskCompleteDatetime is null")
    LiveData<List<TaskEntity>> getAllTasks();
}
