package com.Taskmanagement.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.Taskmanagement.entity.KptEntity;
import com.Taskmanagement.entity.KptHstryEntity;
import com.Taskmanagement.entity.KptLinkEntity;
import com.Taskmanagement.entity.TagEntity;

@Dao
public interface KptDao {
    @Insert
    void insert(KptEntity entity);

    @Insert
    void insert(KptHstryEntity entity);
    @Query("SELECT MAX(sralNum) FROM kpt_history_table WHERE kptId = :kptId")
    Integer getMaxBForA(String kptId);
    @Transaction
    default void insertWithSequence(KptHstryEntity entity) {
        Integer maxSralNum = getMaxBForA(entity.getKptId());
        int nextSralNum = (maxSralNum == null ? 1 : maxSralNum + 1);
        entity.setSralNum(nextSralNum);
        insert(entity);
    }

    @Insert
    void insert(KptLinkEntity entity);
    @Insert
    void insert(TagEntity entity);

    @Query("SELECT * FROM kpt_table")
    KptEntity getAaa();
}
