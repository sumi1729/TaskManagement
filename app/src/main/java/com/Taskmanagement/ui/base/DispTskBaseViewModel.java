package com.Taskmanagement.ui.base;

import static com.Taskmanagement.util.CommonUtility.FIRST_LOOP;
import static com.Taskmanagement.util.CommonUtility.ScreenId;
import static com.Taskmanagement.util.DbUtility.PRIORITY_NOT_SET_JP;
import static com.Taskmanagement.util.DbUtility.priorityMap;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.Taskmanagement.R;
import com.Taskmanagement.entity.display.ScdledTask4Desp;
import com.Taskmanagement.entity.item.HeaderItem;
import com.Taskmanagement.entity.item.ListItem;
import com.Taskmanagement.repository.TaskRepository;
import com.Taskmanagement.util.CommonUtility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DispTskBaseViewModel extends AndroidViewModel {
    protected final TaskRepository repository;

    public DispTskBaseViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
    }

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
                                (PRIORITY_NOT_SET_JP.equals(priority) ? "" : "優先度 ") + priority);
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
     * View可視性切り替え
     *
     * @param view view
     * @return view
     */
    public View toggleButtonVisibility4DispTskBase(View view) {
        switch (CommonUtility.getNowScreenId()) {
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

// ================================================================
// DB操作_UPDATE
// ================================================================
    public void updtTskEntyTskCompDttm(String taskId, LocalDateTime nowDateTime) {
        repository.updtTskEntyTskCompDttm(taskId, nowDateTime);
    }
    public void updtTskEntyTskCompDttmIsNull(String taskId, LocalDateTime nowDateTime) {
        repository.updtTskEntyTskCompDttmIsNull(taskId, nowDateTime);
    }
}