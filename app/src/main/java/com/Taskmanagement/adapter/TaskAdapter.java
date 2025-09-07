package com.Taskmanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Taskmanagement.R;
import com.Taskmanagement.entity.TskEntity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<TskEntity> taskList = new ArrayList<>();

    // デフォルトコンストラクタ
    public TaskAdapter() {
    }

    public TaskAdapter(List<TskEntity> taskList) {
        this.taskList = taskList;
    }

    public void setTasks(List<TskEntity> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    public TskEntity getItem(int position) {
        return taskList.get(position);
    }

    public void removeItem(int position) {
        if (taskList != null && position >= 0 && position < taskList.size()) {
            taskList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TskEntity task = taskList.get(position);
        holder.taskTitle.setText(task.getTskNm());
//        holder.taskTime.setText(task.getTime());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle, taskTime, taskDetail;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.task_title);
            taskTime = itemView.findViewById(R.id.task_time);
            taskDetail = itemView.findViewById(R.id.task_detail);
        }
    }
}
