package com.Taskmanagement.viewModel;

import static com.Taskmanagement.util.CommonUtility.DATE_TIME_FORMATTER_HH_MM;
import static com.Taskmanagement.util.CommonUtility.DATE_TIME_FORMATTER_YYYY_M_D;
import static com.Taskmanagement.util.CommonUtility.DATE_TIME_FORMATTER_YYYY_M_DD_HH_MM;
import static com.Taskmanagement.util.CommonUtility.FIRST_LOOP;
import static com.Taskmanagement.util.CommonUtility.OTHER;
import static com.Taskmanagement.util.CommonUtility.ScreenId;
import static com.Taskmanagement.util.CommonUtility.TAG;
import static com.Taskmanagement.util.DbUtility.priorityMap;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Taskmanagement.R;
import com.Taskmanagement.entity.ScdlEntity;
import com.Taskmanagement.entity.display.ScdledTask4Desp;
import com.Taskmanagement.entity.item.HeaderItem;
import com.Taskmanagement.entity.item.ListItem;
import com.Taskmanagement.entity.TskEntity;
import com.Taskmanagement.repository.TaskRepository;
import com.Taskmanagement.util.CommonUtility;
import com.Taskmanagement.util.DbUtility;
import com.Taskmanagement.util.DbUtility.SCDL_STAT;

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

// ================================================================
// DB操作_INSERT
// ================================================================
    /**
     * 表示リスト作成
     *
     * @param tskId タスクID
     * @param tskNm タスク名
     * @param tskDtl タスク詳細
     * @param tskCgryId タスクカテゴリーID
     * @param tskExecFrcyId タスク実行頻度ID
     * @param prty 優先度
     * @param date 日付
     * @param time 時刻
     * @param nowDttm 現在日時
     */
    public void insertTskEntity(
            String tskId
            , String tskNm
            , String tskDtl
            , String tskCgryId
            , String tskExecFrcyId
            , String prty
            , String date
            , String time
            , LocalDateTime nowDttm
    ) {
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
    /**
     * 表示リスト作成
     *
     * @param tskId タスクID
     * @param tskExecDt タスク実行日付
     * @param tskExecTm タスク実行時刻
     * @param scdlStat スケジュール状態
     * @param nowDttm 現在日時
     */
    public void insertScdlEntity(
            String tskId
            , String tskExecDt
            , String tskExecTm
            , SCDL_STAT scdlStat
            , LocalDateTime nowDttm
    ) {
        LocalDate ldTskExecDt = null;
        LocalTime ldTskExecTm = null;
        try {
            ldTskExecDt = LocalDate.parse(tskExecDt, DATE_TIME_FORMATTER_YYYY_M_D);
        } catch (DateTimeParseException e) {
            Log.e(TAG, "tskExecDt is not set.");
            updateSnackbarEventAsync("日時未定タスクとして登録します");
            return;
        }
        try {
            ldTskExecTm = LocalTime.parse(tskExecTm, DATE_TIME_FORMATTER_HH_MM);
        } catch (DateTimeParseException e) {
            ldTskExecTm = null;
            Log.e(TAG, "tskExecTm is not set.");
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

// ================================================================
// DB操作_UPDATE
// ================================================================
    public void updtTskEntyTskCompDttm(String taskId, LocalDateTime taskCompleteDatetime) {
        repository.updtTskEntyTskCompDttm(taskId, taskCompleteDatetime);
    }

// ================================================================
// DB操作_SELECT
// ================================================================
    public LiveData<List<ScdledTask4Desp>> getTsk4AllTsk() {
        return repository.getTsk4AllTsk();
    }
    public LiveData<List<ScdledTask4Desp>> getUnasinedTsk4AllTsk() {
        return repository.getUnasinedTsk4AllTsk();
    }

    public LiveData<List<ScdledTask4Desp>> getIncompTsk4ScdlRtnLiveData(LocalDate targetDate) {
        return repository.getIncompTsk4ScdlRtnLiveData(targetDate);
    }
    public LiveData<List<ScdledTask4Desp>> getAllTsk4ScdlRtnLiveData(LocalDate targetDate) {
        return repository.getAllTsk4ScdlRtnLiveData(targetDate);
    }

    // DB操作内容確認用
    public void dbOpeTest() {
        repository.dbOpeTest();
    }

// ================================================================
// 画面独自
// ================================================================
    // AllTask画面
    private final MutableLiveData<List<ScdledTask4Desp>> tsk4AllTsk = new MutableLiveData<>();
    public LiveData<List<ScdledTask4Desp>> getDispTsk4AllTsk() {
        return tsk4AllTsk;
    }
    public void updateTsk4AllTskAsync(List<ScdledTask4Desp> newTasks) {
        tsk4AllTsk.postValue(newTasks);
    }

    // Schedule画面
    private final MutableLiveData<List<ScdledTask4Desp>> tsk4Scdl = new MutableLiveData<>();
    public LiveData<List<ScdledTask4Desp>> getDispTsk4Scdl() {
        return tsk4Scdl;
    }
    public void updateTsk4ScdlAsync() {
        tsk4Scdl.postValue(null);
    }

    public List<ScdledTask4Desp> getAllTsk4ScdlRtnList(LocalDate targetDate) {
        return repository.getAllTsk4ScdlRtnList(targetDate);
    }
    public List<ScdledTask4Desp> getIncompTsk4ScdlRtnList(LocalDate targetDate) {
        return repository.getIncompTsk4ScdlRtnList(targetDate);
    }

    // RegisterTaskダイアログ
    private final MutableLiveData<String> snackbarEvent = new MutableLiveData<>();
    public LiveData<String> getSnackbarEvent() {
        return snackbarEvent;
    }
    public void updateSnackbarEventAsync(String message) {
        snackbarEvent.postValue(message);
    }

// ================================================================
// 多画面共通
// ================================================================
    /**
     * 表示リスト作成
     *
     * @param tasks タスクリスト
     * @param screenId 画面ID
     * @return 表示リスト
     */
    public List<ListItem> createDisplayList(List<ScdledTask4Desp> tasks, ScreenId screenId) {
        List<ListItem> displayList = new ArrayList<>();
        String prevValue = FIRST_LOOP;
        String crntValue = null;
        for (ScdledTask4Desp task : tasks) {
            // 初回ループ もしくは 前のデータと値（優先度/タスク実行日付）が異なる場合
            switch (screenId) {
                case ALL_TASK:
                    crntValue = task.prtyId == null ? "" : task.prtyId;
                    if (FIRST_LOOP.equals(prevValue) || !crntValue.equals(prevValue)) {
                        String priority = priorityMap.get(crntValue);
                        String title = String.format("----- %s -----",
                                priority == null ? OTHER : "優先度 " + priority);
                        ListItem listItem = new HeaderItem(title);
                        displayList.add(listItem);
                    }
                    prevValue = crntValue;
                    break;
                default:
                    break;
            }
            displayList.add(task);
        }
        return displayList;
    }

    /**
     * ボタン可視性切り替え
     *
     * @param view view
     * @param screenId スクリーンID
     * @return view
     */
    public View toggleButtonVisibility(View view, ScreenId screenId) {
        switch (screenId) {
            case ALL_TASK:
                view.findViewById(R.id.all_task_switch_toggle).setVisibility(View.VISIBLE);
                view.findViewById(R.id.all_task_switch_label).setVisibility(View.VISIBLE);
                break;
            case SCHEDULE:
                view.findViewById(R.id.schedule_target_date).setVisibility(View.VISIBLE);
                view.findViewById(R.id.schedule_calender_button).setVisibility(View.VISIBLE);
                view.findViewById(R.id.schedule_switch_toggle).setVisibility(View.VISIBLE);
                view.findViewById(R.id.schedule_switch_label).setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        return view;
    }

}