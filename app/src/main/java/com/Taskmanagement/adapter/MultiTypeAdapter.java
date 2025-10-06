package com.Taskmanagement.adapter;

import static com.Taskmanagement.util.CommonUtility.DATE_TIME_FORMATTER_HH_MM;
import static com.Taskmanagement.util.CommonUtility.DATE_TIME_FORMATTER_YY_MM_DD;
import static com.Taskmanagement.util.CommonUtility.DATE_TIME_MITEI;
import static com.Taskmanagement.util.CommonUtility.TAG;
import static com.Taskmanagement.util.CommonUtility.TIME_MITEI;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.Taskmanagement.R;
import com.Taskmanagement.entity.display.ScdledTask4Desp;
import com.Taskmanagement.entity.item.HeaderItem;
import com.Taskmanagement.entity.item.ListItem;
import com.Taskmanagement.util.CommonUtility;

import java.time.LocalDate;
import java.util.List;

public class MultiTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ListItem> items;
    private View view;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ListItem item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public MultiTypeAdapter(List<ListItem> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == ListItem.TYPE_HEADER) {
            View view = inflater.inflate(R.layout.item_task_header, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == ListItem.TYPE_TASK) {
            view = inflater.inflate(R.layout.item_task, parent, false);
            return new TaskViewHolder(view);
        }

        throw new IllegalArgumentException("Unknown viewType: " + viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItem item = items.get(position);
        Log.d(TAG, "Binding item at position " + position + ", type: " + item.getType());

        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).textHeader.setText(((HeaderItem) item).title);
        } else if (holder instanceof TaskViewHolder) {
            ScdledTask4Desp task = (ScdledTask4Desp) item;

            String task_time_1line = "";
            String task_date_2lines = "";
            String task_time_2lines = "";
            switch (CommonUtility.getNowScreenId()) {
                case ALL_TASK:
                    if (task.tskExecDt == null) {
                        // そのタスクがスケジュールされていない場合
                        task_time_1line = DATE_TIME_MITEI;
                        view.findViewById(R.id.task_time_1line).setVisibility(View.VISIBLE);
                    } else {
                        // そのタスクがスケジュールされている場合
                        task_date_2lines = task.tskExecDt.format(DATE_TIME_FORMATTER_YY_MM_DD);
                        task_time_2lines = task.tskExecTm == null ? TIME_MITEI : task.tskExecTm.format(DATE_TIME_FORMATTER_HH_MM);
                        view.findViewById(R.id.task_datetime_2lines).setVisibility(View.VISIBLE);
                    }
                    break;
                case SCHEDULE:
                    if (task.tskExecDt == null) {
                        // ここには入らない（先行処理ではじく）
                    } else {
                        task_time_1line = task.tskExecTm == null ? TIME_MITEI : task.tskExecTm.format(DATE_TIME_FORMATTER_HH_MM);
                        view.findViewById(R.id.task_time_1line).setVisibility(View.VISIBLE);
                        if (!CommonUtility.isNullOrEmpty(task.tskCompDttm)) {
                            ((TaskViewHolder) holder).card_view.setCardBackgroundColor(Color.parseColor("#bbbbbb"));
                        } else {
                            ((TaskViewHolder) holder).card_view.setCardBackgroundColor(Color.parseColor("#ffffff"));
                        }
                    }
                    break;
                default:
                    break;
            }
            //
            if (task.tskExecDt != null && task.tskExecDt.isBefore(LocalDate.now())) {
                // タスク実行日付が過去日である場合、タスクの背景を赤にする
                ((TaskViewHolder) holder).card_view.setCardBackgroundColor(Color.parseColor("#ff0000"));
            }
            ((TaskViewHolder) holder).task_time_1line.setText(task_time_1line);
            ((TaskViewHolder) holder).task_date_2lines.setText(task_date_2lines);
            ((TaskViewHolder) holder).task_time_2lines.setText(task_time_2lines);
            ((TaskViewHolder) holder).taskTitle.setText(task.tskNm);
            ((TaskViewHolder) holder).taskDetail.setText(task.tskDtl);
            ((TaskViewHolder) holder).bind(item, listener);
        }
    }

    public void setItems(List<ListItem> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
         return items.size();
    }

    public void removeItem(int position) {
        if (items != null && position >= 0 && position < items.size()) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView textHeader;

        HeaderViewHolder(View itemView) {
            super(itemView);
            textHeader = itemView.findViewById(R.id.item_header);
        }
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView task_time_1line, task_date_2lines, task_time_2lines, taskTitle, taskDetail;
        CardView card_view;

        TaskViewHolder(View itemView) {
            super(itemView);
            task_time_1line = itemView.findViewById(R.id.task_time_1line);
            task_date_2lines = itemView.findViewById(R.id.task_date_2lines);
            task_time_2lines = itemView.findViewById(R.id.task_time_2lines);
            taskTitle = itemView.findViewById(R.id.task_title);
            taskDetail = itemView.findViewById(R.id.task_detail);
            card_view = itemView.findViewById(R.id.card_view);

        }
        void bind(final ListItem item, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
