package com.Taskmanagement.repository;

import android.app.Application;

import com.Taskmanagement.dao.KptDao;
import com.Taskmanagement.database.AppDatabase;
import com.Taskmanagement.entity.KptEntity;
import com.Taskmanagement.entity.KptHstryEntity;
import com.Taskmanagement.entity.KptLinkEntity;
import com.Taskmanagement.entity.TagEntity;

public class KptRepository {
    private final KptDao kptDao;

    public KptRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        kptDao = db.kptDao();
    }
    public void insert(KptEntity task) {
        new Thread(() -> kptDao.insert(task)).start();
    }
    public void insertWithSequence(KptHstryEntity task) {
        new Thread(() -> kptDao.insertWithSequence(task)).start();
    }
    public void insert(KptLinkEntity task) {
        new Thread(() -> kptDao.insert(task)).start();
    }
    public void insert(TagEntity task) {
        new Thread(() -> kptDao.insert(task)).start();
    }
    public KptEntity getAaa() {
        return kptDao.getAaa();
    }
}