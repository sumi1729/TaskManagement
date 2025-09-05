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

    @Query("SELECT * FROM task_table ORDER BY priorityId DESC")
    LiveData<List<TaskEntity>> getAllTasks();
}
