package com.Taskmanagement.viewModel;

import static com.Taskmanagement.util.CommonUtility.FIRST_LOOP;
import static com.Taskmanagement.util.CommonUtility.OTHER;
import static com.Taskmanagement.util.DbUtility.priorityMap;

import android.app.Application;

import com.Taskmanagement.entity.item.HeaderItem;
import com.Taskmanagement.entity.item.ListItem;
import com.Taskmanagement.entity.TskEntity;
import com.Taskmanagement.repository.TaskRepository;
import com.Taskmanagement.util.CommonUtility;
import com.Taskmanagement.util.DbUtility;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TaskViewModel extends AndroidViewModel {
    private final TaskRepository repository;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
    }

    public void insertTskEntity(
            String tskNm
            ,String tskDtl
            ,String tskCgryId
            ,String tskExecFrcyId
            ,String prty
            ,String date
            ,String time) {
        String prtyId = "";
        for (Map.Entry<String, String> priorityEntry : DbUtility.priorityMap.entrySet()) {
            if (priorityEntry.getValue().equals(prty)) {
                prtyId = priorityEntry.getKey();
            }
        }
        LocalDateTime tskCompDttm = null;
        if (CommonUtility.isNullOrEmpty(date) || CommonUtility.isNullOrEmpty(time)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd HH:mm");
            tskCompDttm = LocalDateTime.parse(date + " " + time, formatter);
        }
        LocalDateTime rstrDttm = LocalDateTime.now();

        TskEntity entity = new TskEntity(
                UUID.randomUUID().toString()
                ,tskNm
                ,tskDtl
                ,tskCgryId
                ,tskExecFrcyId
                ,prtyId
                ,tskCompDttm
                ,rstrDttm
                ,rstrDttm);
        repository.insert(entity);
    }

    public LiveData<List<TskEntity>> getAllIncompTask() {
        return repository.getAllIncompTask();
    }

    /**
     * 表示リスト作成
     *
     * @param tasks タスクリスト
     * @return 表示リスト
     */
    public List<ListItem> createDisplayList(List<TskEntity> tasks) {
        List<ListItem> displayList = new ArrayList<>();
        String previousPrtyId = FIRST_LOOP;
        for (TskEntity task : tasks) {
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