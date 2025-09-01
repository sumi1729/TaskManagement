package com.Taskmanagement.ui.unassignedTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UnassignedTaskViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public UnassignedTaskViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is UnassignedTask fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}