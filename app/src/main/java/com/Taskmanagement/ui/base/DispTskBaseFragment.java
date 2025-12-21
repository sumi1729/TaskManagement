package com.Taskmanagement.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Taskmanagement.adapter.MultiTypeAdapter;
import com.Taskmanagement.databinding.FragmentDispTskBaseBinding;
import com.Taskmanagement.entity.display.ScdledTask4Desp;
import com.Taskmanagement.entity.item.ListItem;
import com.Taskmanagement.ui.registerTask.RegisterTaskDialogFragment;
import com.Taskmanagement.util.CommonUtility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class DispTskBaseFragment extends Fragment {

    protected FragmentDispTskBaseBinding binding;
    protected DispTskBaseViewModel viewModel;
    protected MultiTypeAdapter adapter;
    protected List<ListItem> displayList;

    protected List<ScdledTask4Desp> allTsks = null;
    protected List<ScdledTask4Desp> unasinedTsks = null;

    protected LocalDate targetDate = null;
    protected LocalDate nowDate = null;
    protected boolean isAllTsk = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDispTskBaseBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        adapter = new MultiTypeAdapter(new ArrayList<ListItem>());
        viewModel = new ViewModelProvider(this).get(DispTskBaseViewModel.class);

        // 右スワイプ時の処理
        ItemTouchHelper.SimpleCallback simpleCallbackRight = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                ListItem updateTarget = displayList.get(position);
                if (updateTarget instanceof ScdledTask4Desp) {
                    String taskId = ((ScdledTask4Desp) updateTarget).getTskId();
                    viewModel.updtTskEntyTskCompDttmIsNull(taskId, LocalDateTime.now());
                }
            }

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int viewType = viewHolder.getItemViewType();

                // タイトル項目のスワイプを無効にする
                if (viewType == ListItem.TYPE_TASK) {
                    return makeMovementFlags(0, ItemTouchHelper.RIGHT);
                } else {
                    return makeMovementFlags(0, 0); // スワイプ無効
                }
            }
        };

        // 左スワイプ時の処理
        ItemTouchHelper.SimpleCallback simpleCallbackLeft = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
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
                    viewModel.updtTskEntyTskCompDttm(taskId, LocalDateTime.now());
                }
            }

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int viewType = viewHolder.getItemViewType();

                // タイトル項目のスワイプを無効にする
                if (viewType == ListItem.TYPE_TASK) {
                    return makeMovementFlags(0, ItemTouchHelper.LEFT);
                } else {
                    return makeMovementFlags(0, 0); // スワイプ無効
                }
            }
        };

        adapter.setOnItemClickListener(item -> {
            if (item instanceof ScdledTask4Desp) {
                RegisterTaskDialogFragment dialog = new RegisterTaskDialogFragment(item);
                dialog.show(getActivity().getSupportFragmentManager(), "RegisterTaskDialog");
            }
        });

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelperLeft = new ItemTouchHelper(simpleCallbackLeft);
        itemTouchHelperLeft.attachToRecyclerView(recyclerView);

        ItemTouchHelper itemTouchHelperRight = new ItemTouchHelper(simpleCallbackRight);
        itemTouchHelperRight.attachToRecyclerView(recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * 表示アイテム設定
     *
     * @param tasks タスクリスト
     * @return 表示リスト
     */
    public void setDispItems(List<ScdledTask4Desp> tasks) {
        displayList = viewModel.createDisplayList(tasks, CommonUtility.getNowScreenId());
        adapter.setItems(displayList);
    }
}