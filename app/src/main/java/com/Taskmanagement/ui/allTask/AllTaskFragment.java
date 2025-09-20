package com.Taskmanagement.ui.allTask;

import static com.Taskmanagement.util.CommonUtility.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.Taskmanagement.ui.base.DispTskBaseFragment;
import com.Taskmanagement.util.CommonUtility.ScreenId;
import com.Taskmanagement.viewModel.TaskViewModel;

import javax.annotation.Nullable;

public class AllTaskFragment extends DispTskBaseFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        taskViewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);

        // DB更新あり　かつ　未割当タスクのみ表示
        taskViewModel.getUnasinedTsk4AllTsk().observe(getViewLifecycleOwner(), tasks -> {
            unasinedTsks = tasks;
            if (!isAllTsk) {
                displayList = taskViewModel.createDisplayList(tasks);
                adapter.setItems(displayList);
            }
            Log.d(TAG, "DB contains " + tasks.size() + " tasks");
//            taskViewModel.dbOpeTest();
        });
        // DB更新あり　かつ　全タスク表示
        taskViewModel.getTsk4AllTsk().observe(getViewLifecycleOwner(), tasks -> {
            tsks = tasks;
            if (isAllTsk) {
                displayList = taskViewModel.createDisplayList(tasks);
                adapter.setItems(displayList);
            }
            Log.d(TAG, "DB contains " + tasks.size() + " tasks");
        });
        // DB更新なし
        taskViewModel.getDispTsk4AllTsk().observe(getViewLifecycleOwner(), tasks -> {
            Log.d(TAG, "DB contains " + tasks.size() + " tasks");
            displayList = taskViewModel.createDisplayList(tasks);
            adapter.setItems(displayList);
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState, ScreenId.ALL_TASK);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}