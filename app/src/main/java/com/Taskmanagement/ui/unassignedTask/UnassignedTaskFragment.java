package com.Taskmanagement.ui.unassignedTask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Taskmanagement.R;
import com.Taskmanagement.adaptor.TaskAdapter;
import com.Taskmanagement.databinding.FragmentUnassignedTaskBinding;
import com.Taskmanagement.viewModel.TaskViewModel;

public class UnassignedTaskFragment extends Fragment {

    private FragmentUnassignedTaskBinding binding;

    TaskViewModel taskViewModel;
    private TaskAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unassigned_task, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new TaskAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // ViewModelから未割り当てタスクを取得
        taskViewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);
        taskViewModel.getUnassignedTasks().observe(getViewLifecycleOwner(), tasks -> {
            adapter.setTasks(tasks);
        });

        return view;
    }

//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        UnassignedTaskViewModel unassignedTaskViewModel =
//                new ViewModelProvider(this).get(UnassignedTaskViewModel.class);
//
//        binding = FragmentUnassignedTaskBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        final TextView textView = binding.textUnassignedTask;
//        unassignedTaskViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//
//        TaskAdapter adapter = new TaskAdapter();
//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//
//        // ViewModelからデータを取得してAdapterに渡す
//        taskViewModel.getAllTasks().observe(this, tasks -> {
//            adapter.setTasks(tasks);
//        });
//
////        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
////            @Override
////            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
////                return false; // ドラッグは不要
////            }
////
////            @Override
////            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
////                int position = viewHolder.getAdapterPosition();
////                TaskEntity task = adapter.getTaskAt(position);
////                viewModel.deleteTask(task); // ViewModel経由でDBから削除
////                adapter.removeTaskAt(position); // Adapterからも削除
////            }
////        };
//
//
//        return root;
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}