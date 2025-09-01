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
	public String priorityId;
	public LocalDateTime taskCompleteDatetime;
	public LocalDateTime registerDatetime;
	public LocalDateTime updateDatetime;

	@NonNull
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(@NonNull String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDetail() {
		return taskDetail;
	}

	public void setTaskDetail(String taskDetail) {
		this.taskDetail = taskDetail;
	}

	public String getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(String priorityId) {
		this.priorityId = priorityId;
	}

	public LocalDateTime getTaskCompleteDatetime() {
		return taskCompleteDatetime;
	}

	public void setTaskCompleteDatetime(LocalDateTime taskCompleteDatetime) {
		this.taskCompleteDatetime = taskCompleteDatetime;
	}

	public LocalDateTime getRegisterDatetime() {
		return registerDatetime;
	}

	public void setRegisterDatetime(LocalDateTime registerDatetime) {
		this.registerDatetime = registerDatetime;
	}

	public LocalDateTime getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(LocalDateTime updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public TaskEntity(String taskId
			, String taskName
			, String taskDetail
			, String priorityId
			, LocalDateTime taskCompleteDatetime
			, LocalDateTime registerDatetime
			, LocalDateTime updateDatetime) {
		this.taskId = taskId;
		this.taskName = taskName;
		this.taskDetail = taskDetail;
		this.priorityId = priorityId;
		this.taskCompleteDatetime = taskCompleteDatetime;
		this.registerDatetime = registerDatetime;
		this.updateDatetime = updateDatetime;
	}

}
