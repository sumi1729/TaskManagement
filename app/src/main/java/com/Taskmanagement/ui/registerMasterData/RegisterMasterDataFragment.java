package com.Taskmanagement.ui.registerMasterData;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.Taskmanagement.databinding.FragmentRegisterMasterDataBinding;

public class RegisterMasterDataFragment extends Fragment {

    private RegisterMasterDataViewModel viewModel;
    protected FragmentRegisterMasterDataBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentRegisterMasterDataBinding.inflate(inflater, container, false);
        View view = binding.getRoot();



        viewModel = new ViewModelProvider(this).get(RegisterMasterDataViewModel.class);
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