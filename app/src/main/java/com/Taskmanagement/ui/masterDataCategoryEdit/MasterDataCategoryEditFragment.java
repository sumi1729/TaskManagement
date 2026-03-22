package com.Taskmanagement.ui.masterDataCategoryEdit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.Taskmanagement.R;
import com.Taskmanagement.databinding.FragmentMasterDataCategoryEditBinding;

public class MasterDataCategoryEditFragment extends Fragment {

    protected FragmentMasterDataCategoryEditBinding binding;
    private MasterDataCategoryEditViewModel viewModel;

    private Button categoryButton;
    private Button submitButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMasterDataCategoryEditBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(MasterDataCategoryEditViewModel.class);

        submitButton = view.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> {
            // TODO 処理
            viewModel.insertCategoryEntity("cgryType", "mainName", "subName");
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