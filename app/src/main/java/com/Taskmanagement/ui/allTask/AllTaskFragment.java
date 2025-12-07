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
import com.Taskmanagement.ui.base.DispTskBaseFragment;
import com.Taskmanagement.ui.base.DispTskBaseViewModel;
import com.Taskmanagement.util.CommonUtility;
import com.Taskmanagement.util.CommonUtility.ScreenId;

import javax.annotation.Nullable;

public class AllTaskFragment extends DispTskBaseFragment {

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(DispTskBaseViewModel.class);
        CommonUtility.setNowScreenId(ScreenId.ALL_TASK);

        // DB更新あり　かつ　未割当タスクのみ表示
        viewModel.getUnasinedTsk4AllTsk().observe(getViewLifecycleOwner(), tasks -> {
            unasinedTsks = tasks;
            if (!isAllTsk) {
                setDispItems(tasks);
            }
            Log.d(TAG, "DB contains " + tasks.size() + " tasks");
//            taskViewModel.dbOpeTest();
        });
        // DB更新あり　かつ　全タスク表示
        viewModel.getTsk4AllTsk().observe(getViewLifecycleOwner(), tasks -> {
            allTsks = tasks;
            if (isAllTsk) {
                setDispItems(tasks);
            }
            Log.d(TAG, "DB contains " + tasks.size() + " tasks");
        });
        // DB更新なし
        viewModel.getDispTsk4AllTsk().observe(getViewLifecycleOwner(), tasks -> {
            Log.d(TAG, "DB contains " + tasks.size() + " tasks");
            setDispItems(tasks);
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view = viewModel.toggleButtonVisibility4DispTskBase(view);
        super.onViewCreated(view, savedInstanceState);

        // トグル
        Switch allTaskSwitchToggle = view.findViewById(R.id.all_task_switch_toggle);
        allTaskSwitchToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // ONのときの処理
                isAllTsk = false;
                viewModel.updateTsk4AllTskAsync(unasinedTsks);
            } else {
                // OFFのときの処理
                isAllTsk = true;
                viewModel.updateTsk4AllTskAsync(allTsks);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}