package com.Taskmanagement.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(
		tableName = "kpt_history_table",
		primaryKeys = {"kptId", "sralNum"}
)
public class KptHstryEntity {
	@NonNull
	public String kptId;
	public int sralNum;
	public String kptType;
	public LocalDate compDt;
	public LocalDateTime rstrDttm;
	public LocalDateTime updtDttm;

	public KptHstryEntity(
			String kptId,
			int sralNum,
			String kptType,
			LocalDate compDt,
			LocalDateTime rstrDttm,
			LocalDateTime updtDttm
	) {
		this.kptId = kptId;
		this.sralNum = sralNum;
		this.kptType = kptType;
		this.compDt = compDt;
		this.rstrDttm = rstrDttm;
		this.updtDttm = updtDttm;
	}
	public String getKptId() {
		return kptId;
	}
	public void setSralNum(int sralNum) {
		this.sralNum = sralNum;
	}
}
