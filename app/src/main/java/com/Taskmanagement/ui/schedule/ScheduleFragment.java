package com.Taskmanagement.ui.schedule;

import static com.Taskmanagement.util.CommonUtility.DELIMITER_HYPHON;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.Taskmanagement.R;
import com.Taskmanagement.databinding.FragmentScheduleBinding;
import com.Taskmanagement.util.CommonUtility;
import com.Taskmanagement.viewModel.TaskViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class ScheduleFragment extends Fragment {

    private FragmentScheduleBinding binding;
    private TaskViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewModel =
                new ViewModelProvider(this).get(TaskViewModel.class);
//        final TextView textView = binding.textSchedule;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        TextView targetDate = view.findViewById(R.id.schedule_target_date);
        Button calenderButton = view.findViewById(R.id.schedule_calender_button);
        LocalDate nowDate = LocalDate.now();
        targetDate.setText(
                CommonUtility.getStrDate(nowDate.getYear(), nowDate.getMonthValue(), nowDate.getDayOfMonth(), DELIMITER_HYPHON, false)
        );

        // 日付選択
        calenderButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(requireContext(), (view1, year, month, dayOfMonth) -> {
                targetDate.setText(CommonUtility.getStrDate(year, month, dayOfMonth, DELIMITER_HYPHON, true));
//                calenderButton.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}