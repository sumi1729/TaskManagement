package com.Taskmanagement.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.Taskmanagement.entity.ScdlEntity;
import com.Taskmanagement.entity.TskEntity;
import com.Taskmanagement.entity.display.ScdledTask4Desp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(TskEntity task);

    @Insert
    void insert(ScdlEntity task);

    /**
     * 画面表示共通クエリ
     */
    String dispQuery = "SELECT TT.tskId, TT.tskNm, TT.tskDtl, TT.tskCgryId, TT.tskExecFrcyId, TT.prtyId, TT.tskCompDttm, ST.tskExecDt, ST.tskExecTm, ST.scdlStat, " +
            "CASE when ST.tskExecDt is null THEN 1 ELSE 0 END AS dateSorter, " +
            "CASE when ST.tskExecTm is null THEN 1 ELSE 0 END AS timeSorter " +
            "FROM task_table TT LEFT JOIN schedule_table ST ON TT.tskId = ST.tskId ";
    // AllTask画面　（スケジュール済み含む）
    @Query(dispQuery +
            "WHERE tskCompDttm is null ORDER BY TT.prtyId DESC, dateSorter, ST.tskExecDt, timeSorter, ST.tskExecTm")
    LiveData<List<ScdledTask4Desp>> getTsk4AllTsk();

    // AllTask画面　（未割当タスクのみ）
    @Query(dispQuery +
            "WHERE ST.tskExecDt is null and tskCompDttm is null ORDER BY TT.prtyId DESC")
    LiveData<List<ScdledTask4Desp>> getUnasinedTsk4AllTsk();

    // Schedule画面　（当日未完了タスク）
    String query4AllTask = dispQuery +
            "WHERE tskCompDttm is null and ST.tskExecDt = :targetDate ORDER BY timeSorter, ST.tskExecTm";
    @Query(query4AllTask)
    LiveData<List<ScdledTask4Desp>> getIncompTsk4ScdlRtnLiveData(LocalDate targetDate);
//    @Query(query4AllTask)
//    List<ScdledTask4Desp> getIncompTsk4ScdlRtnList(LocalDate targetDate);

    // Schedule画面　（当日全タスク）
    String query4Schedule = dispQuery +
            "WHERE ST.tskExecDt = :targetDate ORDER BY timeSorter, ST.tskExecTm";
    @Query(query4Schedule)
    LiveData<List<ScdledTask4Desp>> getAllTsk4ScdlRtnLiveData(LocalDate targetDate);
//    @Query(query4Schedule)
//    List<ScdledTask4Desp> getAllTsk4ScdlRtnList(LocalDate targetDate);

    // Schedule画面（全タスク）
    @Query(dispQuery)
    LiveData<List<ScdledTask4Desp>> getScdlTsk4ScdlRtnLiveData();

    @Query("UPDATE task_table SET tskCompDttm = :tskCompDttm WHERE tskId = :tskId")
    void updtTskEntyTskCompDttm(String tskId, LocalDateTime tskCompDttm);

    // DB操作内容確認用
//    @Query("")
//    int dbOpeTest();

}
