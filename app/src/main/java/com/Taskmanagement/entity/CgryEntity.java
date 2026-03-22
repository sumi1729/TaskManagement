package com.Taskmanagement.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "category_table")
public class CgryEntity {
	@PrimaryKey
	@NonNull
	public String cgryId;
	public String cgryType;
	public String mainName;
	public String subName;
	public boolean logDelFlg;
	public LocalDateTime rstrDttm;
	public LocalDateTime updtDttm;

	public CgryEntity(@NonNull String cgryId,
					  String cgryType,
					  String mainName,
					  String subName,
					  boolean logDelFlg,
					  LocalDateTime rstrDttm,
					  LocalDateTime updtDttm
	) {
		this.cgryId = cgryId;
		this.cgryType = cgryType;
		this.mainName = mainName;
		this.subName = subName;
		this.logDelFlg = logDelFlg;
		this.rstrDttm = rstrDttm;
		this.updtDttm = updtDttm;
	}

}
