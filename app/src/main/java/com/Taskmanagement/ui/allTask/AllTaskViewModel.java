package com.Taskmanagement.ui.allTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AllTaskViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AllTaskViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is AllTask fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}