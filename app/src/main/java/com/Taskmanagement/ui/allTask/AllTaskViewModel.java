package com.Taskmanagement.ui.allTask;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Taskmanagement.entity.display.ScdledTask4Desp;
import com.Taskmanagement.ui.base.DispTskBaseViewModel;

import java.util.List;

public class AllTaskViewModel extends DispTskBaseViewModel {

    public AllTaskViewModel(@NonNull Application application) {
        super(application);
    }

// ================================================================
// DB操作_SELECT
// ================================================================
    public LiveData<List<ScdledTask4Desp>> getTsk4AllTsk() {
        return repository.getTsk4AllTsk();
    }
    public LiveData<List<ScdledTask4Desp>> getUnasinedTsk4AllTsk() {
        return repository.getUnasinedTsk4AllTsk();
    }

// ================================================================
// observe
// ================================================================
    private final MutableLiveData<List<ScdledTask4Desp>> tsk4AllTsk = new MutableLiveData<>();
    public LiveData<List<ScdledTask4Desp>> getDispTsk4AllTsk() {
        return tsk4AllTsk;
    }
    public void updateTsk4AllTskAsync(List<ScdledTask4Desp> newTasks) {
        tsk4AllTsk.setValue(newTasks);
    }
}