package com.Taskmanagement.viewModel;

import static com.Taskmanagement.util.CommonUtility.FIRST_LOOP;
import static com.Taskmanagement.util.CommonUtility.OTHER;
import static com.Taskmanagement.util.DbUtility.priorityMap;

import android.app.Application;

import com.Taskmanagement.model.HeaderItem;
import com.Taskmanagement.model.ListItem;
import com.Taskmanagement.model.TaskEntity;
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

    public void insertTaskEntity(
            String taskName
            ,String taskDetail
            ,String taskCategoryId
            ,String taskExecutionFrequencyId
            ,String priority
            ,String date
            ,String time) {
        String priorityId = "";
        for (Map.Entry<String, String> priorityEntry : DbUtility.priorityMap.entrySet()) {
            if (priorityEntry.getValue().equals(priority)) {
                priorityId = priorityEntry.getKey();
            }
        }
        LocalDateTime taskCompleteDatetime = null;
        if (CommonUtility.isNullOrEmpty(date) || CommonUtility.isNullOrEmpty(time)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd HH:mm");
            taskCompleteDatetime = LocalDateTime.parse(date + " " + time, formatter);
        }
        LocalDateTime registerDatetime = LocalDateTime.now();

        TaskEntity entity = new TaskEntity(
                UUID.randomUUID().toString()
                ,taskName
                ,taskDetail
                ,taskCategoryId
                ,taskExecutionFrequencyId
                ,priorityId
                ,taskCompleteDatetime
                ,registerDatetime
                ,registerDatetime);
        repository.insert(entity);
    }

    public LiveData<List<TaskEntity>> getAllIncompTask() {
        return repository.getAllIncompTask();
    }

    /**
     * 表示リスト作成
     *
     * @param tasks タスクリスト
     * @return 表示リスト
     */
    public List<ListItem> createDisplayList(List<TaskEntity> tasks) {
        List<ListItem> displayList = new ArrayList<>();
        String previousPriorityId = FIRST_LOOP;
        for (TaskEntity task : tasks) {
            String currentPriorityId = task.priorityId == null ? "" : task.priorityId;
            // 初回ループ もしくは 優先度が異なる場合のみ優先度タイトルを表示
            if (FIRST_LOOP.equals(previousPriorityId)
                    || !currentPriorityId.equals(previousPriorityId)) {
                String priority = priorityMap.get(currentPriorityId);
                String title = String.format("----- %s -----",
                        priority == null ? OTHER : "優先度 " + priority);
                ListItem listItem = new HeaderItem(title);
                displayList.add(listItem);
            }
            // タスク要素を表示
            displayList.add(task);
            previousPriorityId = currentPriorityId;
        }
        return displayList;
    }

    public void updtTskEntyTskCompDttm(String taskId, LocalDateTime taskCompleteDatetime) {
        repository.updtTskEntyTskCompDttm(taskId, taskCompleteDatetime);

    }

}