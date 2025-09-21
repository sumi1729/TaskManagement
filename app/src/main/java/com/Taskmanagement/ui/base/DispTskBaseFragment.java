package com.Taskmanagement.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Taskmanagement.adapter.MultiTypeAdapter;
import com.Taskmanagement.databinding.FragmentDispTskBaseBinding;
import com.Taskmanagement.entity.display.ScdledTask4Desp;
import com.Taskmanagement.entity.item.ListItem;
import com.Taskmanagement.util.CommonUtility.ScreenId;
import com.Taskmanagement.viewModel.TaskViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class DispTskBaseFragment extends Fragment {

    protected FragmentDispTskBaseBinding binding;
    protected TaskViewModel taskViewModel;
    protected MultiTypeAdapter adapter;
    protected List<ListItem> displayList;

    protected List<ScdledTask4Desp> allTsks = null;
    protected List<ScdledTask4Desp> unasinedTsks = null;
    protected List<ScdledTask4Desp> incompTsks = null;
    protected List<ScdledTask4Desp> tmpTsks = null;

    protected LocalDate targetDate = null;
    protected LocalDate nowDate = null;
    protected boolean isAllTsk = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDispTskBaseBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        adapter = new MultiTypeAdapter(new ArrayList<ListItem>());

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

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState, ScreenId screenId) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}