package com.Taskmanagement.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.Taskmanagement.entity.ScdlEntity;
import com.Taskmanagement.entity.TskEntity;
import com.Taskmanagement.entity.display.ScdledTask4Desp;

import java.time.LocalDateTime;
import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(TskEntity task);

    @Insert
    void insert(ScdlEntity task);

    // AllTask画面　（スケジュール済み含む）
    @Query("SELECT TT.tskId, TT.tskNm, TT.tskDtl, TT.tskCgryId, TT.tskExecFrcyId, TT.prtyId, TT.tskCompDttm, ST.tskExecDt, ST.tskExecTm, ST.scdlStat " +
            "FROM task_table TT LEFT JOIN schedule_table ST ON TT.tskId = ST.tskId " +
            "WHERE tskCompDttm is null ORDER BY TT.prtyId DESC, ST.tskExecDt, ST.tskExecTm")
    LiveData<List<ScdledTask4Desp>> getTsk4AllTsk();

    // AllTask画面　（未割当タスクのみ）
    @Query("SELECT TT.tskId, TT.tskNm, TT.tskDtl, TT.tskCgryId, TT.tskExecFrcyId, TT.prtyId, TT.tskCompDttm, ST.tskExecDt, ST.tskExecTm, ST.scdlStat " +
            "FROM task_table TT LEFT JOIN schedule_table ST ON TT.tskId = ST.tskId " +
            "WHERE ST.tskExecDt is null and tskCompDttm is null ORDER BY TT.prtyId DESC")
    LiveData<List<ScdledTask4Desp>> getUnasinedTsk4AllTsk();

    // DB操作内容確認用
//    @Query("")
//    int dbOpeTest();

    // ScheduledTask画面　（当日以外含む）　// TODO 別途実装が必要
    // ScheduledTask画面　（当日分のみ）　// TODO 別途実装が必要

    @Query("UPDATE task_table SET tskCompDttm = :tskCompDttm WHERE tskId = :tskId")
    void updtTskEntyTskCompDttm(String tskId, LocalDateTime tskCompDttm);

}
