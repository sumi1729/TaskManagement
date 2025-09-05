package com.Taskmanagement.ui.allTask;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Taskmanagement.adaptor.MultiTypeAdapter;
import com.Taskmanagement.databinding.FragmentAllTaskBinding;
import com.Taskmanagement.model.ListItem;
import com.Taskmanagement.viewModel.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllTaskFragment extends Fragment {

    private FragmentAllTaskBinding binding;

    private TaskViewModel taskViewModel;
    private MultiTypeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllTaskBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        adapter = new MultiTypeAdapter(new ArrayList<ListItem>());
        taskViewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);

        taskViewModel.getAllTasks().observe(getViewLifecycleOwner(), tasks -> {
            Log.d("AllTaskFragment", "DB contains " + tasks.size() + " tasks");
            List<ListItem> displayList = taskViewModel.createDisplayList(tasks);
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
                adapter.removeItem(position);
                // TODO DBから削除する処理を追記
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}