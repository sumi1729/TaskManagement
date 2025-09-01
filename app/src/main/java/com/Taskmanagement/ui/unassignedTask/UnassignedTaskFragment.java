package com.Taskmanagement.ui.unassignedTask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Taskmanagement.R;
import com.Taskmanagement.adaptor.TaskAdapter;
import com.Taskmanagement.databinding.FragmentUnassignedTaskBinding;
import com.Taskmanagement.model.TaskEntity;

import java.util.ArrayList;
import java.util.List;

public class UnassignedTaskFragment extends Fragment {

    private FragmentUnassignedTaskBinding binding;

    private TaskAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unassigned_task, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        List<TaskEntity> taskList = new ArrayList<>();
        // TODO DBから取得するよう修正
        taskList.add(new TaskEntity("1", "dummy task1", null, null, null, null, null, null));
        taskList.add(new TaskEntity("2", "dummy task2", null, null, null, null, null, null));
        taskList.add(new TaskEntity("3", "dummy task3", null, null, null, null, null, null));

        adapter = new TaskAdapter(taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

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
                adapter.removeItem(position);
                // RoomDB使用時はここでDBからも削除
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}