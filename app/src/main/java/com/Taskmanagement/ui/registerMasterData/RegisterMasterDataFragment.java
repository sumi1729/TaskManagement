package com.Taskmanagement.ui.registerMasterData;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.Taskmanagement.R;
import com.Taskmanagement.databinding.FragmentRegisterMasterDataBinding;

public class RegisterMasterDataFragment extends Fragment {

    private RegisterMasterDataViewModel viewModel;
    protected FragmentRegisterMasterDataBinding binding;

    private Button categoryButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentRegisterMasterDataBinding.inflate(inflater, container, false);
        View view = binding.getRoot();



        viewModel = new ViewModelProvider(this).get(RegisterMasterDataViewModel.class);
        categoryButton = view.findViewById(R.id.category_button);

        // カテゴリーボタン押下
        categoryButton.setOnClickListener(v -> {
            // カテゴリーマスタ登録画面
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_registerMasterDataFragment_to_masterDataCategoryViewFragment);

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