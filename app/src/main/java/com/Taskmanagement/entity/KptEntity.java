package com.Taskmanagement.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(tableName = "kpt_table")
public class KptEntity {
	@PrimaryKey
	@NonNull
	public String kptId;
	public String kptNm;
	public String kptDtl;
	public LocalDate compDt;
	public LocalDateTime rstrDttm;
	public LocalDateTime updtDttm;

	public KptEntity(@NonNull String kptId,
					 String kptNm,
					 String kptDtl,
					 LocalDate compDt,
					 LocalDateTime rstrDttm,
					 LocalDateTime updtDttm
	) {
		this.kptId = kptId;
		this.kptNm = kptNm;
		this.kptDtl = kptDtl;
		this.compDt = compDt;
		this.rstrDttm = rstrDttm;
		this.updtDttm = updtDttm;
	}

}
