package com.Taskmanagement.ui.registerTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.Taskmanagement.dao.TaskDao;
import com.Taskmanagement.database.AppDatabase;

public class RegisterTaskDialogViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RegisterTaskDialogViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ResisterTask fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public AppDatabase getAppDatabase() {

//        AppDatabase db = Room.databaseBuilder(
//                requireContext().getApplicationContext(),
//                AppDatabase.class,
//                "my_database"
//        ).build();
        return null;

    }
    public void aaa() {
        TaskDao taskDao = getAppDatabase().taskDao();

    }
}