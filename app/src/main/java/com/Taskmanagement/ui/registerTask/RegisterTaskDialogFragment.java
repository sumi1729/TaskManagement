package com.Taskmanagement.ui.registerTask;

import static com.Taskmanagement.util.CommonUtility.ASYNC;
import static com.Taskmanagement.util.CommonUtility.SYNC;
import static com.Taskmanagement.util.CommonUtility.TAG;
import static com.Taskmanagement.util.CommonUtility.getStrDate;
import static com.Taskmanagement.util.CommonUtility.getStrTime;
import static com.Taskmanagement.util.DisplayConst.DELIMITER_HYPHON;
import static com.Taskmanagement.util.DisplayConst.INPUT_FORMAT_LIST;
import static com.Taskmanagement.util.DisplayConst.INPUT_FORMAT_SPINNER_KPT;
import static com.Taskmanagement.util.DisplayConst.INPUT_FORMAT_SPINNER_TASK;
import static com.Taskmanagement.util.DisplayConst.KPT_TYPE_LIST;
import static com.Taskmanagement.util.DisplayConst.PRIORITY_LIST;
import static com.Taskmanagement.util.DisplayConst.TAG_LIST;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import com.Taskmanagement.R;
import com.Taskmanagement.database.DbExecutor;
import com.Taskmanagement.entity.display.ScdledTask4Desp;
import com.Taskmanagement.entity.item.ListItem;
import com.Taskmanagement.ui.base.DispTskBaseViewModel;
import com.Taskmanagement.ui.kpt.KptViewModel;
import com.Taskmanagement.util.CommonUtility;
import com.Taskmanagement.util.DbUtility.SCDL_STAT;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class RegisterTaskDialogFragment extends BottomSheetDialogFragment {

    private RegisterTaskDialogViewModel viewModel;
    private KptViewModel kptViewModel;
    private static final long CLICK_INTERVAL = 2000;
    private long lastClickTime = 0;
    String inputFormatStr;

    String tskExecDtStr;
    String tskExecTmStr;

    private String inputFormat = null;
    private String tskId = null;
    private String tskNm = null;
    private String tskDtl = null;
    private String prtyId = null;
    private LocalDate tskExecDt = null;
    private LocalTime tskExecTm = null;
    private boolean updateFlg = false;

    private List<String> tagList = new ArrayList<String>();
    private List<String> kptLinkList = new ArrayList<String>();

    View thisDialogView;
    Spinner inputFormatSelectSpinner;
    Spinner kptTypeSpinner;
    EditText taskNameInput;
    EditText taskDetailInput;
    Spinner prtySpinner;
    Button dateButton;
    Button timeButton;
    Button clearButton;
    Spinner tagSpinner;
    Button tagButton;
    LinearLayout tagCardContainer;
    Spinner kptLinkSpinner;
    Button kptLinkButton;
    LinearLayout kptLinkCardContainer;
    Button submitButton;


    public RegisterTaskDialogFragment(ListItem item) {
        if (item != null) {
            // TODO タスク登録・KPT登録で取得方法を変える
            inputFormat = "0"; // TODO DBから取得するが、一旦「タスク」固定
            tskId = ((ScdledTask4Desp) item).getTskId();
            tskNm = ((ScdledTask4Desp) item).getTskNm();
            tskDtl = ((ScdledTask4Desp) item).getTskDtl();
            prtyId = ((ScdledTask4Desp) item).getPrtyId();
            tskExecDt = ((ScdledTask4Desp) item).getTskExecDt();
            tskExecTm = ((ScdledTask4Desp) item).getTskExecTm();
            updateFlg = true;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // カードのレイアウトを読み込み
        return inflater.inflate(R.layout.dialog_register_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(RegisterTaskDialogViewModel.class);
        kptViewModel = new ViewModelProvider(requireActivity()).get(KptViewModel.class);
        thisDialogView = view;

        // 入力フォーマット選択（共通）
        inputFormatSelectSpinner = view.findViewById(R.id.input_format_select_spinner);
        // KPTタイプ選択（KPTのみ）
        kptTypeSpinner = view.findViewById(R.id.kpt_type_spinner);
        // タイトル・詳細入力（共通）
        taskNameInput = view.findViewById(R.id.input_task_name);
        taskDetailInput = view.findViewById(R.id.input_task_detail);
        // 日時選択（タスクのみ）
        dateButton = view.findViewById(R.id.date_button);
        timeButton = view.findViewById(R.id.time_button);
        clearButton = view.findViewById(R.id.clear_button);
        // 優先度選択（タスクのみ）
        prtySpinner = view.findViewById(R.id.priority_spinner);
        // タグ選択（KPTのみ）
        tagSpinner = view.findViewById(R.id.tag_spinner);
        tagButton = view.findViewById(R.id.tag_button);
        tagCardContainer = view.findViewById(R.id.tag_card_container);
        // KPT紐づけ選択（KPTのみ）
        kptLinkSpinner = view.findViewById(R.id.kpt_link_spinner);
        kptLinkButton = view.findViewById(R.id.kpt_link_button);
        kptLinkCardContainer = view.findViewById(R.id.kpt_link_card_container);
        // 登録ボタン（共通）
        submitButton = view.findViewById(R.id.submit_button);

        setupButton(tagButton, tagSpinner, tagCardContainer, tagList);
        setupButton(kptLinkButton, kptLinkSpinner, kptLinkCardContainer, kptLinkList);

        // Spinner設定
        setupSpinner(inputFormatSelectSpinner, INPUT_FORMAT_LIST);
        setupSpinner(kptTypeSpinner, KPT_TYPE_LIST);
        setupSpinner(prtySpinner, PRIORITY_LIST);
        setupSpinner(tagSpinner, TAG_LIST);
        setupSpinner(kptLinkSpinner, getkptLinkList());

        // 入力フォーマット選択スピナーの値により、各Viewの可視性を設定する
        setViewVisibility();

        if (updateFlg) {
            inputFormatSelectSpinner.setSelection(Integer.valueOf(inputFormat)); // 入力テンプレート
            taskNameInput.setText(tskNm); // タスク名
            taskDetailInput.setText(tskDtl); // タスク詳細
            prtySpinner.setSelection(Integer.valueOf(prtyId)); // 優先度ID
            dateButton.setText(CommonUtility.getStrDate(tskExecDt)); // 日付
            timeButton.setText(getStrTime(tskExecTm)); // 時刻
        }
        // キーボード表示
//        view.post(() -> {
//            taskNameInput.requestFocus();
//            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.showSoftInput(taskNameInput, InputMethodManager.SHOW_IMPLICIT);
//        });
        // 日付選択
        setupDatePicker();
        // 時間選択
        setupTimePicker();
        // クリアボタン押下
        setupClearButton();
        // 送信ボタン押下
        setupSubmitButton();
        // オブザーバー設定
        setObserver(view);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void setViewVisibility() {
        // 入力内容を選択のとこに書いてある内容によって処理分岐
        View layoutKptType = thisDialogView.findViewById(R.id.layout_kpt_type);
        View layoutInputTaskName = thisDialogView.findViewById(R.id.layout_input_task_name);
        View layoutInputTaskDetail = thisDialogView.findViewById(R.id.layout_input_task_detail);
        View layoutDateTime = thisDialogView.findViewById(R.id.layout_date_time);
        View layoutPriority = thisDialogView.findViewById(R.id.layout_priority);
        View layoutTag = thisDialogView.findViewById(R.id.layout_tag);
        View layoutKptLink = thisDialogView.findViewById(R.id.layout_kpt_link);

        inputFormatStr = inputFormatSelectSpinner.getSelectedItem().toString();
        switch (inputFormatStr) {
            case INPUT_FORMAT_SPINNER_TASK:
                layoutKptType.setVisibility(View.GONE);
                layoutInputTaskName.setVisibility(View.VISIBLE);
                layoutInputTaskDetail.setVisibility(View.VISIBLE);
                layoutDateTime.setVisibility(View.VISIBLE);
                layoutPriority.setVisibility(View.VISIBLE);
                layoutTag.setVisibility(View.GONE);
                layoutKptLink.setVisibility(View.GONE);
                break;
            case INPUT_FORMAT_SPINNER_KPT:
                layoutKptType.setVisibility(View.VISIBLE);
                layoutInputTaskName.setVisibility(View.VISIBLE);
                layoutInputTaskDetail.setVisibility(View.VISIBLE);
                layoutDateTime.setVisibility(View.GONE);
                layoutPriority.setVisibility(View.GONE);
                layoutTag.setVisibility(View.VISIBLE);
                layoutKptLink.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void setupSpinner(@NonNull Spinner spinner, @NonNull List<String> inputFormatList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                inputFormatList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (spinner.equals(inputFormatSelectSpinner)) {
            // 選択変更を検知するリスナーをセット
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // 選択項目（旧）
                    String oldSelectedItem = inputFormatStr;
                    // 選択項目（新）
                    String newSelectedItem = inputFormatSelectSpinner.getSelectedItem().toString();

                    // タイトル・詳細欄の文字列取得
                    String title = taskNameInput.getText().toString();
                    String detail = taskDetailInput.getText().toString();
                    Log.d("", "title=" + title);
                    Log.d("", "detail=" + detail);
                    if (!oldSelectedItem.equals(newSelectedItem)
                            && (!"".equals(title) || !"".equals(detail))
                    ) {
//                        // 入力フォーマット選択スピナーの値により、各Viewの可視性を設定する
                        showDialog("", "入力内容がリセットされます。\nよろしいですか？");
                    } else {
                        setViewVisibility();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // 何も選択されていないときの処理
                }
            });
        }
    }

    private void showDialog(String title, String message) {
        // タイトルと詳細欄に文字が入っていない場合
        boolean isDialog = true;
//        ((EditText) )
                
        if (isDialog) {
            // AlertDialogを表示
            new AlertDialog.Builder(getContext())
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("はい", (dialog, which) -> {
                        setViewVisibility();
                        creatAllInputField();
                        dialog.dismiss();
                    })
                    .setNegativeButton("いいえ", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        }
    }

    private void creatAllInputField() {
        // タイトル、詳細欄、KPT区分、タグ、Tとの紐づけ、優先度、日時
        kptTypeSpinner.setSelection(0);
        taskNameInput.setText("");
        taskDetailInput.setText("");
        prtySpinner.setSelection(0);
        dateButton.setText(getString(R.string.register_task_dialog_fragment_select_date));
        timeButton.setText(getString(R.string.register_task_dialog_fragment_select_time));
        tagSpinner.setSelection(0);
        tagCardContainer.removeAllViews();
        kptLinkSpinner.setSelection(0);
        kptLinkCardContainer.removeAllViews();
    }

    private void setupDatePicker() {
        dateButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
                dateButton.setText(getStrDate(year, month, dayOfMonth, DELIMITER_HYPHON, true));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void setupTimePicker() {
        timeButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new TimePickerDialog(requireContext(), (view, hourOfDay, minute) -> {
                timeButton.setText(getStrTime(hourOfDay, minute));
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        });
    }

    private void setupClearButton() {
        clearButton.setOnClickListener(v -> {
            dateButton.setText(R.string.register_task_dialog_fragment_select_date);
            timeButton.setText(R.string.register_task_dialog_fragment_select_time);
        });
    }

    private void setupButton(Button button, Spinner spinner, LinearLayout cardContainer, List<String> list) {
        button.setOnClickListener(v -> {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View cardView = inflater.inflate(R.layout.item_registration, cardContainer, false);

            TextView textCard = cardView.findViewById(R.id.textCard);
            String in = spinner.getSelectedItem().toString();

            if (!list.contains(in)) {
                // リストに入れる
                list.add(in);
                textCard.setText(in);

                // 削除ボタン押下処理
                ImageButton buttonDelete = cardView.findViewById(R.id.buttonDelete);
                buttonDelete.setOnClickListener(v2 -> {
                    cardContainer.removeView(cardView);
                    // ×を押した項目の名称を取得し、リストから削除する
                    String deleteTarget = textCard.toString();
                    list.remove(deleteTarget);
                });

                // コンテナに追加
                cardContainer.addView(cardView);
                cardContainer.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupSubmitButton() {
        if (updateFlg) {
            submitButton.setText(getString(R.string.update));
        } else {
            submitButton.setText(getString(R.string.register));
        }
        submitButton.setOnClickListener(v -> {
            if (disableButton()) {
                return;
            }
            // 現在日時取得
            LocalDateTime nowDttm = LocalDateTime.now();
            String tskId = updateFlg ? this.tskId : UUID.randomUUID().toString();
            String tskNm = taskNameInput.getText().toString();
            String tskDtl = taskDetailInput.getText().toString();
            String reviewCgryId = "dummy";
            String reviewComment = "dummy";
            String tskExecFrcyId = "dummy";
            String prty = prtySpinner.getSelectedItem().toString();
            tskExecDtStr = dateButton.getText().toString();
            tskExecTmStr = timeButton.getText().toString();

            switch (inputFormatStr) {
                case INPUT_FORMAT_SPINNER_TASK:
                    if (updateFlg) {
                        // タスクテーブル更新
                        viewModel.updateTskEntity(tskId, tskNm ,tskDtl ,tskExecFrcyId, prty, reviewCgryId, reviewComment, nowDttm);

                        // DB更新件数によって処理分岐を行う必要があるため、Fragment側でThread#startによってDB操作実施
                        DbExecutor.execute(() -> {
                            // スケジュールテーブル更新
                            if (viewModel.updateScdlEntity(tskId, tskExecDtStr, tskExecTmStr, LocalDateTime.now(), SYNC) == 0) {
                                // スケジュールテーブルにレコードがない場合、スケジュールテーブル登録
                                viewModel.insertScdlEntity(tskId, tskExecDtStr, tskExecTmStr, SCDL_STAT.NOT_DONE, LocalDateTime.now(), SYNC);
                            }
                            new Handler(Looper.getMainLooper()).post(() -> {
                                dismiss();
                            });
                        });
                        return;
                    } else {
                        // タスクテーブル登録
                        viewModel.insertTskEntity(tskId, tskNm ,tskDtl ,tskExecFrcyId, prty ,null ,null, reviewCgryId, reviewComment, nowDttm);
                        // スケジュールテーブル登録
                        viewModel.insertScdlEntity(tskId, tskExecDtStr, tskExecTmStr, SCDL_STAT.NOT_DONE, nowDttm, ASYNC);
                    }
                    break;
                case INPUT_FORMAT_SPINNER_KPT:
                    if (updateFlg) {
                        // KPTテーブル更新

                    } else {
                        // KPTテーブル登録
                        kptViewModel.insertKptEntity(tskId, tskNm, tskDtl, nowDttm);
                        // KPT履歴テーブル登録
                        String kptType = kptTypeSpinner.getSelectedItem().toString();
                        kptViewModel.insertKptHstryEntity(tskId, kptType, nowDttm);
                        // タグテーブル登録
                        for (int i = 0; i <= tagCardContainer.getChildCount(); i++) {
                            View cardView = tagCardContainer.getChildAt(i);
                            TextView textCard = cardView.findViewById(R.id.textCard);
                            String text = textCard.getText().toString();
                            kptViewModel.insertTagEntity(tskId, text, nowDttm);
                        }
//                        kptViewModel.insertKptLinkEntity("", "", nowDttm); TODO 後日実装
                    }
                    break;
            }
            dismiss();
        });
    }

    private void setObserver(View view) {
        // Snackbar設定
        viewModel.getSnackbarEvent().observe(getViewLifecycleOwner(), message -> {
            Log.d(TAG, "snackbar");
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getTheme() {
        return R.style.CustomBottomSheetDialogTheme;
    }

    private List<String> getkptLinkList() {
        // TODO 暫定
        return new ArrayList(Arrays.asList("①", "➁", "③"));
    }

    /**
     * ボタン連打防止チェック
     *
     * @return true：連打／false：非連打
     */
    public boolean disableButton() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < CLICK_INTERVAL) {
            return true;
        }
        lastClickTime = currentTime;
        return false;
    }
}