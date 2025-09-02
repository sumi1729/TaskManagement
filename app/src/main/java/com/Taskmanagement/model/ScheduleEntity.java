package com.Taskmanagement.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity(tableName = "schedule_table")
public class ScheduleEntity {
//    @PrimaryKey(autoGenerate = true)
//	public int taskId; // または long
    @PrimaryKey
	@NonNull
	public String taskId;
	public LocalDate scheduledDate;
	public LocalTime scheduledTime;
	public String scheduleState;
	public LocalDateTime registerDatetime;
	public LocalDateTime updateDatetime;

	public ScheduleEntity(
			String taskId
			,LocalDate scheduledDate
			,LocalTime scheduledTime
			,String scheduleState
			,LocalDateTime registerDatetime
			,LocalDateTime updateDatetime) {
		this.taskId = taskId;
		this.scheduledDate = scheduledDate;
		this.scheduledTime = scheduledTime;
		this.scheduleState = scheduleState;
		this.registerDatetime = registerDatetime;
		this.updateDatetime = updateDatetime;
	}

	@NonNull
	public String getTaskId() {
		return taskId;
	}

	public LocalDate getScheduledDate() {
		return scheduledDate;
	}

	public LocalTime getScheduledTime() {
		return scheduledTime;
	}

	public String getScheduleState() {
		return scheduleState;
	}

	public LocalDateTime getRegisterDatetime() {
		return registerDatetime;
	}

	public LocalDateTime getUpdateDatetime() {
		return updateDatetime;
	}
}
