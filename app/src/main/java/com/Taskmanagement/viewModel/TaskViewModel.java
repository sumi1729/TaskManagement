package com.Taskmanagement.viewModel;

import android.app.Application;

import com.Taskmanagement.database.AppDatabase;
import com.Taskmanagement.model.TaskEntity;
import com.Taskmanagement.repository.TaskRepository;
import com.Taskmanagement.util.DbUtility;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TaskViewModel extends AndroidViewModel {
    private final AppDatabase database;
    private final TaskRepository repository;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        database = Room.databaseBuilder(
                application,
                AppDatabase.class,
                "my_database"
        ).build();
        repository = new TaskRepository(application);
    }

    public void insertTaskEntity(
            String taskName
            ,String taskDetail
            ,String taskCategoryId
            ,String priority
            ,String date
            ,String time) {
        String priorityId = "";
        for (Map.Entry<String, String> priorityEntry : DbUtility.priorityMap.entrySet()) {
            if (priorityEntry.getValue().equals(priority)) {
                priorityId = priorityEntry.getKey();
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd HH:mm");
        LocalDateTime taskCompleteDatetime = LocalDateTime.parse(date + " " + time, formatter);
        LocalDateTime registerDatetime = LocalDateTime.now();

        TaskEntity entity = new TaskEntity(UUID.randomUUID().toString()
                ,taskName
                ,taskDetail
                ,taskCategoryId
                ,priorityId
                ,taskCompleteDatetime
                ,registerDatetime
                ,registerDatetime);
        repository.insert(entity);
    }

    public void select() {
        repository.getAllTasks_();
    }
    public LiveData<List<TaskEntity>> getUnassignedTasks() {
        return repository.getUnassignedTasks();
    }
}