package com.Taskmanagement.repository;

import android.app.Application;

import com.Taskmanagement.dao.TaskDao;
import com.Taskmanagement.database.AppDatabase;
import com.Taskmanagement.database.DbExecutor;
import com.Taskmanagement.entity.ScdlEntity;
import com.Taskmanagement.entity.TskEntity;
import com.Taskmanagement.entity.display.ScdledTask4Desp;

import androidx.lifecycle.LiveData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class TaskRepository {
    private final TaskDao taskDao;

    public TaskRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        taskDao = db.taskDao();
    }

    public void insert(TskEntity task) {
        DbExecutor.execute(() -> taskDao.insert(task));
    }
    public void insert(ScdlEntity task) {
        DbExecutor.execute(() -> taskDao.insert(task));
    }
    public void insertSync(ScdlEntity task) {
        taskDao.insert(task);
    }

    public void updateTskEntity(String tskId, String tskNm, String tskDtl, String tskExecFrcyId, String prtyId, String reviewCgryId, String reviewComment, LocalDateTime updtDttm) {
        DbExecutor.execute(() -> taskDao.updateTskEntity(tskId, tskNm, tskDtl, tskExecFrcyId, prtyId, reviewCgryId, reviewComment, updtDttm));
    }
    public int updateScdlEntitySync(String tskId, LocalDate tskExecDt, LocalTime tskExecTm, LocalDateTime updtDttm) {
        return taskDao.updateScdlEntity(tskId, tskExecDt, tskExecTm, updtDttm);
    }

    public void updtTskEntyTskCompDttm(String taskId, LocalDateTime nowDateTime) {
        DbExecutor.execute(() -> taskDao.updtTskEntyTskCompDttm(taskId, nowDateTime));
    }
    public void updtTskEntyTskCompDttmIsNull(String taskId, LocalDateTime nowDateTime) {
        DbExecutor.execute(() -> taskDao.updtTskEntyTskCompDttmIsNull(taskId, nowDateTime));
    }

    public LiveData<List<ScdledTask4Desp>> getTsk4AllTsk() {
        return taskDao.getTsk4AllTsk();
    }
    public LiveData<List<ScdledTask4Desp>> getUnasinedTsk4AllTsk() {
        return taskDao.getUnasinedTsk4AllTsk();
    }

    public LiveData<List<ScdledTask4Desp>> getIncompTsk4ScdlRtnLiveData(LocalDate targetDate) {
        return taskDao.getIncompTsk4ScdlRtnLiveData(targetDate);
    }
    public LiveData<List<ScdledTask4Desp>> getAllTsk4ScdlRtnLiveData(LocalDate targetDate) {
        return taskDao.getAllTsk4ScdlRtnLiveData(targetDate);
    }
//    public List<ScdledTask4Desp> getIncompTsk4ScdlRtnList(LocalDate targetDate) {
//        return taskDao.getIncompTsk4ScdlRtnList(targetDate);
//    }
//    public List<ScdledTask4Desp> getAllTsk4ScdlRtnList(LocalDate targetDate) {
//        return taskDao.getAllTsk4ScdlRtnList(targetDate);
//    }
    public LiveData<List<ScdledTask4Desp>> getScdlTsk4ScdlRtnLiveData() {
        return taskDao.getScdlTsk4ScdlRtnLiveData();
    }

    public void dbOpeTest() {
//        new Thread(() -> taskDao.dbOpeTest()).start();
    }

}