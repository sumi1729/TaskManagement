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
import com.Taskmanagement.dto.ScdlFilterDto;
import com.Taskmanagement.ui.base.DispTskBaseFragment;
import com.Taskmanagement.util.CommonUtility;
import com.Taskmanagement.util.CommonUtility.ScreenId;
import com.Taskmanagement.viewModel.TaskViewModel;

import java.time.LocalDate;

public class ScheduleFragment extends DispTskBaseFragment {

    private ScdlFilterDto scdlFilterDto = new ScdlFilterDto();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        nowDate = LocalDate.now();
        targetDate = nowDate;
        CommonUtility.setNowScreenId(ScreenId.SCHEDULE);

        // DB更新あり　かつ　当日全タスク表示
        // ・初期表示
        // ・スケジュール登録・更新・削除後のタスク表示
        taskViewModel.getAllTsk4ScdlRtnLiveData(targetDate).observe(getViewLifecycleOwner(), tasks -> {
//            allTsks = tasks;
            if (isAllTsk) {
                if (nowDate == targetDate) {
                    // 日付変更を伴わない場合
                    setDispItems(tasks);
                } else  {
                    // 日付変更を伴う場合
                    scdlFilterDto.setScdlFilter(targetDate, isAllTsk);
                    taskViewModel.setScdlFilter(scdlFilterDto);
                }
                Log.d(TAG, "DB contains " + tasks.size() + " tasks");
            }
        });
        // DB更新あり　かつ　当日未完了タスクのみ表示
        taskViewModel.getIncompTsk4ScdlRtnLiveData(targetDate).observe(getViewLifecycleOwner(), tasks -> {
//            incompTsks = tasks;
            if (!isAllTsk) {
                setDispItems(tasks);
            }
            Log.d(TAG, "DB contains " + tasks.size() + " tasks");
        });
        // DB更新なし
        // ・カレンダーで日付指定しての全タスク／当日未完了タスク表示
        taskViewModel.getDispTsk4ScdlRtnLiveData().observe(getViewLifecycleOwner(), tasks -> {
            setDispItems(tasks);
            Log.d(TAG, "DB contains " + tasks.size() + " tasks");
        });
        // DB更新なし
        // ・トグル切り替え後のタスク表示
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
        view = taskViewModel.toggleButtonVisibility4DispTskBase(view);
        super.onViewCreated(view, savedInstanceState);

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

                scdlFilterDto.setScdlFilter(targetDate, isAllTsk);
                taskViewModel.setScdlFilter(scdlFilterDto);
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
}