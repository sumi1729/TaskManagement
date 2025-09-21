package com.Taskmanagement.ui.schedule;

import static com.Taskmanagement.util.CommonUtility.DELIMITER_HYPHON;
import static com.Taskmanagement.util.CommonUtility.TAG;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.Taskmanagement.R;
import com.Taskmanagement.entity.display.ScdledTask4Desp;
import com.Taskmanagement.ui.base.DispTskBaseFragment;
import com.Taskmanagement.util.CommonUtility;
import com.Taskmanagement.util.CommonUtility.ScreenId;
import com.Taskmanagement.viewModel.TaskViewModel;

import java.time.LocalDate;
import java.util.List;

public class ScheduleFragment extends DispTskBaseFragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        nowDate = LocalDate.now();
        targetDate = nowDate;

        // DB更新あり　かつ　当日全タスク表示
        taskViewModel.getAllTsk4ScdlRtnLiveData(targetDate).observe(getViewLifecycleOwner(), tasks -> {
            allTsks = tasks;
            if (isAllTsk) {
                setDispItems(tasks);
            }
            Log.d(TAG, "DB contains " + tasks.size() + " tasks");
        });
        // DB更新あり　かつ　当日未完了タスクのみ表示
        taskViewModel.getIncompTsk4ScdlRtnLiveData(targetDate).observe(getViewLifecycleOwner(), tasks -> {
            incompTsks = tasks;
            if (!isAllTsk) {
                setDispItems(tasks);
            }
            Log.d(TAG, "DB contains " + tasks.size() + " tasks");
        });
        // DB更新なし
        taskViewModel.getDispTsk4Scdl().observe(getViewLifecycleOwner(), tasks -> {
//            Log.d(TAG, "DB contains " + tasks.size() + " tasks");
            new Thread(() -> {
                if (isAllTsk) {
                    allTsks = taskViewModel.getAllTsk4ScdlRtnList(targetDate);
                    tmpTsks = allTsks;
                } else {
                    incompTsks = taskViewModel.getIncompTsk4ScdlRtnList(targetDate);
                    tmpTsks = incompTsks;
                }
            }).start();

            CommonUtility.wait(1000);
            setDispItems(tmpTsks);
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view = taskViewModel.toggleButtonVisibility(view, ScreenId.SCHEDULE);
        super.onViewCreated(view, savedInstanceState, ScreenId.SCHEDULE);

        TextView targetDateText = view.findViewById(R.id.schedule_target_date);
        targetDateText.setText(
                CommonUtility.getStrDate(targetDate.getYear(), targetDate.getMonthValue(), targetDate.getDayOfMonth(), DELIMITER_HYPHON, false)
        );
        // 日付選択
        Button calenderButton = view.findViewById(R.id.schedule_calender_button);
        calenderButton.setOnClickListener(v -> {
            new DatePickerDialog(requireContext(), (view1, year, month, dayOfMonth) -> {
                targetDateText.setText(
                        CommonUtility.getStrDate(year, month, dayOfMonth, DELIMITER_HYPHON, true)
                );
                targetDate = LocalDate.of(year, month + 1, dayOfMonth);
                taskViewModel.updateTsk4ScdlAsync();
            }, targetDate.getYear(), targetDate.getMonthValue() - 1, targetDate.getDayOfMonth()).show(); // カレンダー初期表示
        });
        // トグル
        Switch scheduleSwitchToggle = view.findViewById(R.id.schedule_switch_toggle);
        scheduleSwitchToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // ONのときの処理
                isAllTsk = false;
//                Toast.makeText(getContext(), "未完了タスクのみを表示しました。", Toast.LENGTH_SHORT).show();
            } else {
                // OFFのときの処理
                isAllTsk = true;
            }
            taskViewModel.updateTsk4ScdlAsync();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 表示アイテム設定
     *
     * @param tasks タスクリスト
     * @return 表示リスト
     */
    public void setDispItems(List<ScdledTask4Desp> tasks) {
        displayList = taskViewModel.createDisplayList(tasks, ScreenId.SCHEDULE);
        adapter.setItems(displayList);
    }
}