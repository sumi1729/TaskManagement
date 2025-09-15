package com.Taskmanagement.repository;

import android.app.Application;

import com.Taskmanagement.dao.TaskDao;
import com.Taskmanagement.database.AppDatabase;
import com.Taskmanagement.entity.ScdlEntity;
import com.Taskmanagement.entity.TskEntity;
import com.Taskmanagement.entity.display.ScdledTask4Desp;

import androidx.lifecycle.LiveData;

import java.time.LocalDateTime;
import java.util.List;

public class TaskRepository {
    private final TaskDao taskDao;

    public TaskRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        taskDao = db.taskDao();
    }

    public void insert(TskEntity task) {
        new Thread(() -> taskDao.insert(task)).start();
    }

    public void insert(ScdlEntity task) {
        new Thread(() -> taskDao.insert(task)).start();
    }

    public void updtTskEntyTskCompDttm(String taskId, LocalDateTime taskCompleteDatetime) {
        new Thread(() -> taskDao.updtTskEntyTskCompDttm(taskId, taskCompleteDatetime)).start();
    }

    public LiveData<List<ScdledTask4Desp>> getTsk4AllTsk() {
        return taskDao.getTsk4AllTsk();
    }

}