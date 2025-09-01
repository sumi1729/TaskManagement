package com.Taskmanagement.ui.registerTask;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.Taskmanagement.R;
import com.Taskmanagement.viewModel.TaskViewModel;
import com.Taskmanagement.viewModel.TaskViewModelFactory;
import com.Taskmanagement.model.TaskEntity;
import com.Taskmanagement.util.DbUtility;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class RegisterTaskDialogFragment extends BottomSheetDialogFragment {

    TaskViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_register_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TaskViewModelFactory factory = new TaskViewModelFactory(requireActivity().getApplication());
        viewModel = new ViewModelProvider(requireActivity(), factory).get(TaskViewModel.class);
//        viewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);

        EditText taskNameInput = view.findViewById(R.id.input_task_name);
        EditText taskDetailInput = view.findViewById(R.id.input_task_detail);
        Spinner spinner = view.findViewById(R.id.spinner);
        Button dateButton = view.findViewById(R.id.date_button);
        Button timeButton = view.findViewById(R.id.time_button);

        // キーボード表示
        view.post(() -> {
            taskNameInput.requestFocus();
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(taskNameInput, InputMethodManager.SHOW_IMPLICIT);
        });

        // 日付選択
        dateButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(requireContext(), (view1, year, month, dayOfMonth) -> {
                dateButton.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        // 時間選択
        timeButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new TimePickerDialog(requireContext(), (view12, hourOfDay, minute) -> {
                timeButton.setText(String.format("%02d:%02d", hourOfDay, minute));
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        });

        // Spinner 設定
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.priority_list,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button submitButton = view.findViewById(R.id.submit_button); // XMLに追加必要
        submitButton.setOnClickListener(v -> {
            String taskName = taskNameInput.getText().toString();
            String taskDetail = taskDetailInput.getText().toString();
            String date = dateButton.getText().toString();
            String time = timeButton.getText().toString();
            String taskCategoryId = "dummy";
            String priority = spinner.getSelectedItem().toString();
            viewModel.insertTaskEntity(taskName ,taskDetail ,taskCategoryId, priority ,date ,time);
            try {
//                Thread.sleep(10);
                viewModel.select();
            } catch (Exception e) {
                viewModel.select();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            EditText editText = view.findViewById(R.id.input_task_name);
            view.post(() -> {
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            });
        }
    }

    private void setupSpinner(Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.priority_list,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setupDatePicker(Button dateButton) {
        dateButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
                dateButton.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void setupTimePicker(Button timeButton) {
        timeButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new TimePickerDialog(requireContext(), (view, hourOfDay, minute) -> {
                timeButton.setText(String.format("%02d:%02d", hourOfDay, minute));
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        });
    }

    @Override
    public int getTheme() {
        return R.style.CustomBottomSheetDialogTheme;
    }
}