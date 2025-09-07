package com.Taskmanagement.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity(tableName = "schedule_table")
public class ScdlEntity {
	//    @PrimaryKey(autoGenerate = true)
//	public int taskId; // または long
	@PrimaryKey
	@NonNull
	public String tskId;
	public LocalDate tskExecDt;
	public LocalTime tskExecTm;
	public String scdlStat;
	public LocalDateTime rstrDttm;
	public LocalDateTime updtDttm;

	public ScdlEntity(
			String tskId
			,LocalDate tskExecDt
			,LocalTime tskExecTm
			,String scdlStat
			,LocalDateTime rstrDttm
			,LocalDateTime updtDttm) {
		this.tskId = tskId;
		this.tskExecDt = tskExecDt;
		this.tskExecTm = tskExecTm;
		this.scdlStat = scdlStat;
		this.rstrDttm = rstrDttm;
		this.updtDttm = updtDttm;

	}

	@NonNull
	public String getTskId() {
		return tskId;
	}

	public LocalDate getTskExecDt() {
		return tskExecDt;
	}

	public LocalTime getTskExecTm() {
		return tskExecTm;
	}

	public String getScdlStat() {
		return scdlStat;
	}

	public LocalDateTime getRstrDttm() {
		return rstrDttm;
	}

	public LocalDateTime getUpdtDttm() {
		return updtDttm;
	}
}
