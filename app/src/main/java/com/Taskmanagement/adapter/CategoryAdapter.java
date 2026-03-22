package com.Taskmanagement.adapter;

import static com.Taskmanagement.util.DisplayConst.ADD_NEW_ITEM;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.Taskmanagement.R;
import com.Taskmanagement.entity.item.MainItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context; // ★これを追加
    private List<MainItem> mainItemList;
    private List<Object> displayList = new ArrayList<>();

    public interface OnSubClickListener {
        void onSubClick(String subName);
    }

    private OnSubClickListener subClickListener;

    public CategoryAdapter(Context context,List<MainItem> mainItemList, OnSubClickListener listener) {
        this.context = context;
        this.mainItemList = mainItemList;
        this.subClickListener = listener;
//        createDisplayList();
    }

    private void createDisplayList() {
        displayList.clear();
        for (int mi = 0; mi < mainItemList.size(); mi++) {
            DisplayItem mainDisplayItem = new DisplayItem();
            mainDisplayItem.type = DisplayItem.TYPE_MAIN;
//            if (mi < mainItemList.size()) {
                mainDisplayItem.mainItem = mainItemList.get(mi);
                displayList.add(mainDisplayItem);

                MainItem mainItem = mainDisplayItem.mainItem;
                if (mainItem.isExpanded) {
                    List<String> subList = mainItem.subNameList;
                    for (int si = 0; si < subList.size() + 1; si++) {
                        DisplayItem subDisplayItem = new DisplayItem();
                        subDisplayItem.type = DisplayItem.TYPE_SUB;
                        if (si < subList.size()) {
                            subDisplayItem.subName = subList.get(si);
                        } else {
                            subDisplayItem.subName = ADD_NEW_ITEM;
                        }
                        displayList.add(subDisplayItem);

                    }
    //                displayList.add(new AddButtonTag(mainItem));
                }
//            } else {
//                mainDisplayItem.mainItem = new MainItem(ADD_NEW_ITEM, null, false);
//                displayList.add(mainDisplayItem);
//            }
        }
    }
    private static class AddButtonTag {
        MainItem mainItem;
        AddButtonTag(MainItem mainItem) {
            this.mainItem = mainItem;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup mainViewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mainViewGroup.getContext());
        if (viewType == DisplayItem.TYPE_MAIN) {
            View v = inflater.inflate(R.layout.item_main, mainViewGroup, false);
            return new MainViewHolder(v);
        } else if (viewType == DisplayItem.TYPE_SUB) {
            View v = inflater.inflate(R.layout.item_sub, mainViewGroup, false);
            return new SubViewHolder(v);
        } else {
            // AddButtonTagの場合にこちらに入る想定
            View v = inflater.inflate(R.layout.item_add_button, mainViewGroup, false);
            return new AddButtonViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        Object item = displayList.get(position);
        if (type == DisplayItem.TYPE_MAIN) {
            MainViewHolder vh = (MainViewHolder) holder;
//            MainItem mainItem = (MainItem) item;
            DisplayItem displayItem = (DisplayItem) item;
            vh.title.setText(displayItem.mainItem.mainName);

            vh.itemView.setOnClickListener(v -> {
                displayItem.mainItem.isExpanded = !displayItem.mainItem.isExpanded;
                createDisplayList();
                notifyDataSetChanged();
            });
        } else if (type == DisplayItem.TYPE_SUB) {
            SubViewHolder vh = (SubViewHolder) holder;
            DisplayItem displayItem = (DisplayItem) item;
            vh.title.setText(displayItem.subName);

            vh.itemView.setOnClickListener(v -> {
                if (subClickListener != null) {
                    subClickListener.onSubClick(displayItem.subName);
                }
            });
        } else if (type == DisplayItem.TYPE_ADD_BUTTON) {
            AddButtonViewHolder h = (AddButtonViewHolder) holder;
            MainItem mainItem = ((AddButtonTag) item).mainItem;
            h.addButton.setOnClickListener(v -> showAddDialog(mainItem));
        }
    }
    // 重複チェック付きの登録ダイアログ
    private void showAddDialog(MainItem mainItem) {
        // Context（Activity）を使用してEditTextを作成
        final EditText input = new EditText(context);
        input.setHint("サブカテゴリ名を入力");

        // ダイアログの外側に余白を作る（お好みで）
        input.setPadding(50, 40, 50, 40);

        new AlertDialog.Builder(context) // ★保存したContextを使用
                .setTitle("新規追加")
                .setMessage(mainItem.mainName + " にサブカテゴリを追加します")
                .setView(input)
                .setPositiveButton("追加", (dialog, which) -> {
                    String newSubName = input.getText().toString().trim();

                    if (newSubName.isEmpty()) return;

                    // 重複チェック
                    if (mainItem.subNameList.contains(newSubName)) {
                        Toast.makeText(context, "既に存在します", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // リスト更新
                    mainItem.subNameList.add(newSubName);
                    createDisplayList(); // 内部リストを再構築
                    notifyDataSetChanged(); // 画面を更新
                })
                .setNegativeButton("キャンセル", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = displayList.get(position);
        if (item instanceof DisplayItem) {
            return ((DisplayItem) item).type;
        } else if (item instanceof AddButtonTag) {
            return DisplayItem.TYPE_ADD_BUTTON;
        }
        return DisplayItem.TYPE_SUB;
    }

    public void updateData(List<MainItem> newList) {
        this.mainItemList = newList;
        createDisplayList();
        notifyDataSetChanged();
    }


    class DisplayItem {
        public static final int TYPE_MAIN = 0;
        public static final int TYPE_SUB = 1;
        public static final int TYPE_ADD_BUTTON = 2;

        public int type;
        MainItem mainItem;
        String subName;
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        MainViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.mainTitle);
        }
    }

    class SubViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        SubViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.subTitle);
        }
    }

    class AddButtonViewHolder extends RecyclerView.ViewHolder {
        Button addButton;
        AddButtonViewHolder(View v) {
            super(v);
            addButton = v.findViewById(R.id.addButtonTitle);
        }
    }
}
