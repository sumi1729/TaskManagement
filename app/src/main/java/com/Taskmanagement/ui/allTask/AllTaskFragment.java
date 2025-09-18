package com.Taskmanagement.ui.allTask;

import static com.Taskmanagement.util.CommonUtility.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Taskmanagement.R;
import com.Taskmanagement.adapter.MultiTypeAdapter;
import com.Taskmanagement.databinding.FragmentAllTaskBinding;
import com.Taskmanagement.entity.display.ScdledTask4Desp;
import com.Taskmanagement.entity.item.ListItem;
import com.Taskmanagement.viewModel.TaskViewModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class AllTaskFragment extends Fragment {

    private FragmentAllTaskBinding binding;

    private TaskViewModel taskViewModel;
    private MultiTypeAdapter adapter;
    private List<ListItem> displayList;

    private List<ScdledTask4Desp> tsks = null;
    private List<ScdledTask4Desp> unasinedTsks = null;
    private boolean isAllTsk = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllTaskBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        adapter = new MultiTypeAdapter(new ArrayList<ListItem>());
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

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                ListItem deleteTarget = displayList.get(position);

                if (deleteTarget instanceof ScdledTask4Desp) {
                    adapter.removeItem(position);
                    String taskId = ((ScdledTask4Desp) deleteTarget).getTskId();
                    taskViewModel.updtTskEntyTskCompDttm(taskId, LocalDateTime.now());
                }
            }
        };

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Switch switchToggle = view.findViewById(R.id.switchToggle);

        switchToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // ONのときの処理
                isAllTsk = false;
                taskViewModel.updateTsk4AllTskAsync(unasinedTsks);
//                Toast.makeText(getContext(), "全タスクを表示しました。", Toast.LENGTH_SHORT).show();
            } else {
                // OFFのときの処理
                isAllTsk = true;
                taskViewModel.updateTsk4AllTskAsync(tsks);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}