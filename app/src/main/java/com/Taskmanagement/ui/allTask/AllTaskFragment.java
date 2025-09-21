package com.Taskmanagement.ui.allTask;

import static com.Taskmanagement.util.CommonUtility.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.Taskmanagement.R;
import com.Taskmanagement.entity.display.ScdledTask4Desp;
import com.Taskmanagement.ui.base.DispTskBaseFragment;
import com.Taskmanagement.util.CommonUtility.ScreenId;
import com.Taskmanagement.viewModel.TaskViewModel;

import java.util.List;

import javax.annotation.Nullable;

public class AllTaskFragment extends DispTskBaseFragment {

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        taskViewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);

        // DB更新あり　かつ　未割当タスクのみ表示
        taskViewModel.getUnasinedTsk4AllTsk().observe(getViewLifecycleOwner(), tasks -> {
            unasinedTsks = tasks;
            if (!isAllTsk) {
                setDispItems(tasks);
            }
            Log.d(TAG, "DB contains " + tasks.size() + " tasks");
//            taskViewModel.dbOpeTest();
        });
        // DB更新あり　かつ　全タスク表示
        taskViewModel.getTsk4AllTsk().observe(getViewLifecycleOwner(), tasks -> {
            allTsks = tasks;
            if (isAllTsk) {
                setDispItems(tasks);
            }
            Log.d(TAG, "DB contains " + tasks.size() + " tasks");
        });
        // DB更新なし
        taskViewModel.getDispTsk4AllTsk().observe(getViewLifecycleOwner(), tasks -> {
            Log.d(TAG, "DB contains " + tasks.size() + " tasks");
            setDispItems(tasks);
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view = taskViewModel.toggleButtonVisibility(view, ScreenId.ALL_TASK);
        super.onViewCreated(view, savedInstanceState, ScreenId.ALL_TASK);

        // トグル
        Switch allTaskSwitchToggle = view.findViewById(R.id.all_task_switch_toggle);
        allTaskSwitchToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // ONのときの処理
                isAllTsk = false;
                taskViewModel.updateTsk4AllTskAsync(unasinedTsks);
            } else {
                // OFFのときの処理
                isAllTsk = true;
                taskViewModel.updateTsk4AllTskAsync(allTsks);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 表示アイテム設定
     *
     * @param tasks タスクリスト
     * @return 表示リスト
     */
    public void setDispItems(List<ScdledTask4Desp> tasks) {
        displayList = taskViewModel.createDisplayList(tasks, ScreenId.ALL_TASK);
        adapter.setItems(displayList);
    }
}