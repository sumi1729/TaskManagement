package com.Taskmanagement.repository;

import android.app.Application;

import com.Taskmanagement.dao.TaskDao;
import com.Taskmanagement.database.AppDatabase;
import com.Taskmanagement.model.TaskEntity;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {
    private final TaskDao taskDao;

    public TaskRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        taskDao = db.taskDao();
    }

    public void insert(TaskEntity task) {
        new Thread(() -> taskDao.insert(task)).start();
    }

    public LiveData<List<TaskEntity>> getAllTasks() {
        return taskDao.getAllTaskLive();
    }

    public void getAllTasks_() {
        new Thread(() -> {
            int i = taskDao.getAllTaskLive_();
        }).start();
    }
    public LiveData<List<TaskEntity>> getUnassignedTasks() {
        return taskDao.getUnassignedTasks();
    }

}