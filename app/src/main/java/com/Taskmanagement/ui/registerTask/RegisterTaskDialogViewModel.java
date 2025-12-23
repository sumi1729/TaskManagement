package com.Taskmanagement.ui.registerTask;

import static com.Taskmanagement.util.CommonUtility.DATE_TIME_FORMATTER_HH_MM;
import static com.Taskmanagement.util.CommonUtility.DATE_TIME_FORMATTER_YYYY_M_DD_HH_MM;
import static com.Taskmanagement.util.CommonUtility.DATE_TIME_FORMATTER_YY_MM_DD;
import static com.Taskmanagement.util.CommonUtility.TAG;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Taskmanagement.entity.ScdlEntity;
import com.Taskmanagement.entity.TskEntity;
import com.Taskmanagement.ui.base.DispTskBaseViewModel;
import com.Taskmanagement.util.CommonUtility;
import com.Taskmanagement.util.DbUtility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class RegisterTaskDialogViewModel extends DispTskBaseViewModel {

    public RegisterTaskDialogViewModel(@NonNull Application application) {
        super(application);
    }

// ================================================================
// DB操作_INSERT
// ================================================================
    /**
     * task_table登録
     *
     * @param tskId タスクID
     * @param tskNm タスク名
     * @param tskDtl タスク詳細
     * @param tskExecFrcyId タスク実行頻度ID
     * @param prty 優先度
     * @param date 日付
     * @param time 時刻
     * @param reviewCgryId レビューカテゴリーID
     * @param reviewComment レビューコメント
     * @param nowDttm 現在日時
     */
    public void insertTskEntity(
            String tskId,
            String tskNm,
            String tskDtl,
            String tskExecFrcyId,
            String prty,
            String date,
            String time,
            String reviewCgryId,
            String reviewComment,
            LocalDateTime nowDttm
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
                tskId,
                tskNm,
                tskDtl,
                tskExecFrcyId,
                prtyId,
                tskCompDttm,
                reviewCgryId,
                reviewComment,
                nowDttm,
                nowDttm
        );
        repository.insert(entity);
    }

    /**
     * schedule_table登録
     *
     * @param tskId タスクID
     * @param tskExecDt タスク実行日付
     * @param tskExecTm タスク実行時刻
     * @param scdlStat スケジュール状態
     * @param nowDttm 現在日時
     */
    public void insertScdlEntity(
            String tskId,
            String tskExecDt,
            String tskExecTm,
            DbUtility.SCDL_STAT scdlStat,
            LocalDateTime nowDttm,
            boolean isSync
    ) {
        LocalDate ldTskExecDt = null;
        LocalTime ldTskExecTm = null;
        try {
            ldTskExecDt = LocalDate.parse(tskExecDt, DATE_TIME_FORMATTER_YY_MM_DD);
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
                tskId,
                ldTskExecDt,
                ldTskExecTm,
                scdlStat.toString(),
                nowDttm,
                nowDttm
        );
        if (isSync) {
            repository.insertSync(entity);
        } else {
            repository.insert(entity);
        }
    }

// ================================================================
// DB操作_UPDATE
// ================================================================
    /**
     * task_table更新
     *
     * @param tskId タスクID
     * @param tskNm タスク名
     * @param tskDtl タスク詳細
     * @param tskExecFrcyId タスク実行頻度ID
     * @param prty 優先度
     * @param reviewCgryId レビューカテゴリーID
     * @param reviewComment レビューコメント
     * @param nowDttm 現在日時
     */
    public void updateTskEntity(
            String tskId,
            String tskNm,
            String tskDtl,
            String tskExecFrcyId,
            String prty,
            String reviewCgryId,
            String reviewComment,
            LocalDateTime nowDttm
    ) {

        String prtyId = "";
        for (Map.Entry<String, String> priorityEntry : DbUtility.priorityMap.entrySet()) {
            if (priorityEntry.getValue().equals(prty)) {
                prtyId = priorityEntry.getKey();
            }
        }
        repository.updateTskEntity(tskId, tskNm, tskDtl, tskExecFrcyId, prtyId, reviewCgryId, reviewComment, nowDttm);
    }

    /**
     * schedule_table更新
     *
     * @param tskId タスクID
     * @param tskExecDt タスク実行日付
     * @param tskExecTm タスク実行時刻
     * @param nowDttm 現在日時
     */
    public int updateScdlEntity(
            String tskId,
            String tskExecDt,
            String tskExecTm,
            LocalDateTime nowDttm,
            boolean isSync
    ) {
        LocalDate ldTskExecDt = null;
        LocalTime ldTskExecTm = null;
        try {
            ldTskExecDt = LocalDate.parse(tskExecDt, DATE_TIME_FORMATTER_YY_MM_DD);
        } catch (DateTimeParseException e) {
            Log.e(TAG, "tskExecDt is not set.");
            updateSnackbarEventAsync("日時未定タスクとして登録します");
            return 0;
        }
        try {
            ldTskExecTm = LocalTime.parse(tskExecTm, DATE_TIME_FORMATTER_HH_MM);
        } catch (DateTimeParseException e) {
            ldTskExecTm = null;
            Log.e(TAG, "tskExecTm is not set.");
        }
        if (isSync) {
            return repository.updateScdlEntitySync(tskId, ldTskExecDt, ldTskExecTm, nowDttm);
        } else {
            return 0; // TODO 必要になった際に実装
        }
    }

// ================================================================
// observe
// ================================================================
    // RegisterTaskダイアログ
    private final MutableLiveData<String> snackbarEvent = new MutableLiveData<>();
    public LiveData<String> getSnackbarEvent() {
        return snackbarEvent;
    }
    public void updateSnackbarEventAsync(String message) {
        snackbarEvent.setValue(message);
    }
}