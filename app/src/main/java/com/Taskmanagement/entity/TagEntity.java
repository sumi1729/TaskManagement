package com.Taskmanagement.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.time.LocalDateTime;

@Entity(
		tableName = "tag_table",
		primaryKeys = {"tskKptId", "tagId"}
)
public class TagEntity {
	@NonNull
	public String tskKptId;
	@NonNull
	public String tagId;
	public LocalDateTime rstrDttm;
	public LocalDateTime updtDttm;



	public TagEntity(
			@NonNull String tskKptId,
			@NonNull String tagId,
			LocalDateTime rstrDttm,
			LocalDateTime updtDttm
	) {
		this.tskKptId = tskKptId;
		this.tagId = tagId;
		this.rstrDttm = rstrDttm;
		this.updtDttm = updtDttm;
	}
}
