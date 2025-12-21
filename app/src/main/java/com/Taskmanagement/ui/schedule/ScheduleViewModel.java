package com.Taskmanagement.ui.schedule;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.Taskmanagement.dto.ScdlFilterDto;
import com.Taskmanagement.entity.display.ScdledTask4Desp;
import com.Taskmanagement.ui.base.DispTskBaseViewModel;

import java.time.LocalDate;
import java.util.List;

public class ScheduleViewModel extends DispTskBaseViewModel {

    public ScheduleViewModel(@NonNull Application application) {
        super(application);
    }

// ================================================================
// DB操作_SELECT
// ================================================================
    public LiveData<List<ScdledTask4Desp>> getAllTsk4ScdlRtnLiveData() {
        return repository.getScdlTsk4ScdlRtnLiveData();
    }

// ================================================================
// observe
// ================================================================
    // トグル切り替えを検知
    private final MutableLiveData<Boolean> isAllTsk = new MutableLiveData<>();
    public LiveData<Boolean> getIsAllTsk() {
        return isAllTsk;
    }
    public void updateIsAllTsk(boolean newIsAllTsk) {
        isAllTsk.setValue(newIsAllTsk);
    }

// ================================================================
// その他
// ================================================================
    private final MutableLiveData<ScdlFilterDto> selectedDate = new MutableLiveData<>();
    public void setScdlFilter(ScdlFilterDto scdlFilterDto) {
        selectedDate.setValue(scdlFilterDto);
    }
    public LiveData<List<ScdledTask4Desp>> getDispTsk4ScdlRtnLiveData() {
        return Transformations.switchMap(selectedDate, scdlFilterDto -> {
            LocalDate date = scdlFilterDto.getDate();
            boolean isAllTsk = scdlFilterDto.getIsAllTsk();
            if (isAllTsk) {
                return repository.getAllTsk4ScdlRtnLiveData(date);
            } else {
                return repository.getIncompTsk4ScdlRtnLiveData(date);
            }
        });
    }
}