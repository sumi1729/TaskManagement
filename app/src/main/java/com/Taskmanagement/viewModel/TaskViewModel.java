package com.Taskmanagement.viewModel;

import static com.Taskmanagement.util.CommonUtility.DATE_TIME_FORMATTER_HH_MM;
import static com.Taskmanagement.util.CommonUtility.DATE_TIME_FORMATTER_YYYY_M_DD;
import static com.Taskmanagement.util.CommonUtility.DATE_TIME_FORMATTER_YYYY_M_DD_HH_MM;
import static com.Taskmanagement.util.CommonUtility.FIRST_LOOP;
import static com.Taskmanagement.util.CommonUtility.OTHER;
import static com.Taskmanagement.util.DbUtility.priorityMap;

import android.app.Application;

import com.Taskmanagement.entity.ScdlEntity;
import com.Taskmanagement.entity.display.ScdledTask4Desp;
import com.Taskmanagement.entity.item.HeaderItem;
import com.Taskmanagement.entity.item.ListItem;
import com.Taskmanagement.entity.TskEntity;
import com.Taskmanagement.repository.TaskRepository;
import com.Taskmanagement.util.CommonUtility;
import com.Taskmanagement.util.DbUtility;
import com.Taskmanagement.util.DbUtility.SCDL_STAT;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskViewModel extends AndroidViewModel {
    private final TaskRepository repository;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
    }

    public void insertTskEntity(
            String tskId
            , String tskNm
            , String tskDtl
            , String tskCgryId
            , String tskExecFrcyId
            , String prty
            , String date
            , String time
            , LocalDateTime nowDttm) {
        String prtyId = "";
        for (Map.Entry<String, String> priorityEntry : DbUtility.priorityMap.entrySet()) {
            if (priorityEntry.getValue().equals(prty)) {
                prtyId = priorityEntry.getKey();
            }
        }
        LocalDateTime tskCompDttm = null;
        if (!CommonUtility.isNullOrEmpty(date) && !CommonUtility.isNullOrEmpty(time)) {
            tskCompDttm = LocalDateTime.parse(date + " " + time, DATE_TIME_FORMATTER_YYYY_M_DD_HH_MM);
        }

        TskEntity entity = new TskEntity(
                tskId
                , tskNm
                , tskDtl
                , tskCgryId
                , tskExecFrcyId
                , prtyId
                , tskCompDttm
                , nowDttm
                , nowDttm);
        repository.insert(entity);
    }

    public void insertScdlEntity(
            String tskId
            , String tskExecDt // タスク実行日付
            , String tskExecTm // タスク実行時刻
            , SCDL_STAT scdlStat
            , LocalDateTime nowDttm
    ) {

        LocalDate ldTskExecDt = null;
        LocalTime ldTskExecTm = null;
        if (CommonUtility.isNullOrEmpty(tskExecDt)) {
            // TODO エラーログ出力
            return;
        } else {
            try {
                ldTskExecDt = LocalDate.parse(tskExecDt, DATE_TIME_FORMATTER_YYYY_M_DD);
            } catch (DateTimeParseException e) {
                return;
            }
        }
        if (!CommonUtility.isNullOrEmpty(tskExecTm)) {
            try {
                ldTskExecTm = LocalTime.parse(tskExecTm, DATE_TIME_FORMATTER_HH_MM);
            } catch (DateTimeParseException e) {
                ldTskExecTm = null;
            }
        }

        ScdlEntity entity = new ScdlEntity(
                tskId
                , ldTskExecDt
                , ldTskExecTm
                , scdlStat.toString()
                , nowDttm
                , nowDttm);
        repository.insert(entity);
    }

    public LiveData<List<ScdledTask4Desp>> getTsk4AllTsk() {
        return repository.getTsk4AllTsk();
    }

    /**
     * 表示リスト作成
     *
     * @param tasks タスクリスト
     * @return 表示リスト
     */
    public List<ListItem> createDisplayList(List<ScdledTask4Desp> tasks) {
        List<ListItem> displayList = new ArrayList<>();
        String previousPrtyId = FIRST_LOOP;
        for (ScdledTask4Desp task : tasks) {
            String currentPrtyId = task.prtyId == null ? "" : task.prtyId;
            // 初回ループ もしくは 優先度が異なる場合のみ優先度タイトルを表示
            if (FIRST_LOOP.equals(previousPrtyId)
                    || !currentPrtyId.equals(previousPrtyId)) {
                String priority = priorityMap.get(currentPrtyId);
                String title = String.format("----- %s -----",
                        priority == null ? OTHER : "優先度 " + priority);
                ListItem listItem = new HeaderItem(title);
                displayList.add(listItem);
            }
            // タスク要素を表示
            displayList.add(task);
            previousPrtyId = currentPrtyId;
        }
        return displayList;
    }

    public void updtTskEntyTskCompDttm(String taskId, LocalDateTime taskCompleteDatetime) {
        repository.updtTskEntyTskCompDttm(taskId, taskCompleteDatetime);

    }

}