package com.Taskmanagement.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(
		tableName = "kpt_link_table",
		primaryKeys = {"kptId_P", "kptId_Kt"}
)
public class KptLinkEntity {
	@NonNull
	public String kptId_P;
	@NonNull
	public String kptId_Kt;
	public LocalDateTime rstrDttm;
	public LocalDateTime updtDttm;


	public KptLinkEntity(
			@NonNull String kptId_P,
			@NonNull String kptId_Kt,
			LocalDateTime rstrDttm,
			LocalDateTime updtDttm
	) {
		this.kptId_P = kptId_P;
		this.kptId_Kt = kptId_Kt;
		this.rstrDttm = rstrDttm;
		this.updtDttm = updtDttm;
	}
}
