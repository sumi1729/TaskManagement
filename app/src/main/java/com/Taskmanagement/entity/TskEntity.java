package com.Taskmanagement.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.Taskmanagement.entity.item.ListItem;

import java.time.LocalDateTime;

@Entity(tableName = "task_table")
public class TskEntity {
	@PrimaryKey
	@NonNull
	public String tskId;
	public String tskNm;
	public String tskDtl;
	public String tskExecFrcyId;
	public String prtyId;
	public LocalDateTime tskCompDttm;
	public String reviewCgryId;
	public String reviewComment;
	public LocalDateTime rstrDttm;
	public LocalDateTime updtDttm;


	public TskEntity(String tskId
			,String tskNm
			,String tskDtl
//			,String tskCgryId
			,String tskExecFrcyId
			,String prtyId
			,LocalDateTime tskCompDttm
			,String reviewCgryId
			,String reviewComment
			,LocalDateTime rstrDttm
			,LocalDateTime updtDttm) {
		this.tskId = tskId;
		this.tskNm = tskNm;
		this.tskDtl = tskDtl;
		this.tskExecFrcyId = tskExecFrcyId;
		this.prtyId = prtyId;
		this.tskCompDttm = tskCompDttm;
		this.reviewCgryId = reviewCgryId;
		this.reviewComment = reviewComment;
		this.rstrDttm = rstrDttm;
		this.updtDttm = updtDttm;

	}

	@NonNull
	public String getTskId() {
		return tskId;
	}

	public String getTskNm() {
		return tskNm;
	}

	public String getTskDtl() {
		return tskDtl;
	}

	public String getTskExecFrcyId() {
		return tskExecFrcyId;
	}

	public String getPrtyId() {
		return prtyId;
	}

	public LocalDateTime getTskCompDttm() {
		return tskCompDttm;
	}

	public LocalDateTime getRstrDttm() {
		return rstrDttm;
	}

	public LocalDateTime getUpdtDttm() {
		return updtDttm;
	}

}
