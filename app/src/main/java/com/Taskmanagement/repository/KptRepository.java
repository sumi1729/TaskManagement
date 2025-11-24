package com.Taskmanagement.repository;

import android.app.Application;

import com.Taskmanagement.dao.KptDao;
import com.Taskmanagement.database.AppDatabase;
import com.Taskmanagement.database.DbExecutor;
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
        DbExecutor.execute(() -> kptDao.insert(task));
    }
    public void insertWithSequence(KptHstryEntity task) {
        DbExecutor.execute(() -> kptDao.insertWithSequence(task));
    }
    public void insert(KptLinkEntity task) {
        DbExecutor.execute(() -> kptDao.insert(task));
    }
    public void insert(TagEntity task) {
        DbExecutor.execute(() -> kptDao.insert(task));
    }
    public KptEntity getAaa() {
        return kptDao.getAaa();
    }
}