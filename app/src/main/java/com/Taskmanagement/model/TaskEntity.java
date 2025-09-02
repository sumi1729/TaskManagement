package com.Taskmanagement.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(tableName = "task_table")
public class TaskEntity {
//    @PrimaryKey(autoGenerate = true)
//	public int taskId; // または long
    @PrimaryKey
	@NonNull
	public String taskId;
	public String taskName;
	public String taskDetail;
	public String taskCategoryId;
	public String taskExecutionFrequencyId;
	public String priorityId;
	public LocalDateTime taskCompleteDatetime;
	public LocalDateTime registerDatetime;
	public LocalDateTime updateDatetime;

	public TaskEntity(String taskId
			, String taskName
			, String taskDetail
			, String taskCategoryId
			, String taskExecutionFrequencyId
			, String priorityId
			, LocalDateTime taskCompleteDatetime
			, LocalDateTime registerDatetime
			, LocalDateTime updateDatetime) {
		this.taskId = taskId;
		this.taskName = taskName;
		this.taskDetail = taskDetail;
		this.taskCategoryId = taskCategoryId;
		this.taskExecutionFrequencyId = taskExecutionFrequencyId;
		this.priorityId = priorityId;
		this.taskCompleteDatetime = taskCompleteDatetime;
		this.registerDatetime = registerDatetime;
		this.updateDatetime = updateDatetime;
	}

	@NonNull
	public String getTaskId() {
		return taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTaskDetail() {
		return taskDetail;
	}

	public String getTaskCategoryId() {
		return taskCategoryId;
	}

	public String getPriorityId() {
		return priorityId;
	}

	public LocalDateTime getTaskCompleteDatetime() {
		return taskCompleteDatetime;
	}

	public LocalDateTime getRegisterDatetime() {
		return registerDatetime;
	}

	public LocalDateTime getUpdateDatetime() {
		return updateDatetime;
	}
}
