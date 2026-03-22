package com.Taskmanagement.ui.masterDataCategoryList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Taskmanagement.R;
import com.Taskmanagement.adapter.CategoryAdapter;
import com.Taskmanagement.databinding.FragmentMasterDataCategoryListBinding;
import com.Taskmanagement.entity.item.MainItem;

import java.util.ArrayList;
import java.util.List;

public class MasterDataCategoryListFragment extends Fragment {

    protected FragmentMasterDataCategoryListBinding binding;
    private MasterDataCategoryListViewModel viewModel;
    private Button addNewItemButton;

    private Button categoryButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMasterDataCategoryListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(MasterDataCategoryListViewModel.class);
        addNewItemButton = view.findViewById(R.id.add_new_item);

        // カテゴリーボタン押下
        addNewItemButton.setOnClickListener(v -> {
            // CategoryEntityにinsertするための画面を表示
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_masterDataCategoryListFragment_to_masterDataCategoryEditFragment);

        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        List<CategoryEntity> categoryList = viewModel.getCategoryList();
//        LiveData<List<MainItem>> mainItemList = viewModel.getItemList();
//        ExpandableAdapter adapter = new ExpandableAdapter(parentItemList);

        CategoryAdapter adapter = new CategoryAdapter(getContext(), new ArrayList<>(), subName -> {
            // 子要素クリック時の処理（画面遷移）
//            NavController navController = NavHostFragment.findNavController(this);
//            navController.navigate(R.id.action_masterDataCategoryListFragment_to_masterDataCategoryEditFragment);
//            if (subName.startsWith("＋")) {
//
//            }
        });
        recyclerView.setAdapter(adapter);

        // ★ ここがポイント：LiveData を observe して UI を更新
        viewModel.getCategoryList().observe(getViewLifecycleOwner(), items -> {
            List<MainItem> itemList = viewModel.getItemList(items);
            adapter.updateData(itemList);  // adapter にデータを渡す
        });

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}