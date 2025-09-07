package com.Taskmanagement.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.Taskmanagement.entity.item.ListItem;

import java.time.LocalDateTime;

@Entity(tableName = "task_table")
public class TskEntity extends ListItem {
	@PrimaryKey
	@NonNull
	public String tskId;
	public String tskNm;
	public String tskDtl;
	public String tskCgryId;
	public String tskExecFrcyId;
	public String prtyId;
	public LocalDateTime tskCompDttm;
	public LocalDateTime rstrDttm;
	public LocalDateTime updtDttm;


	public TskEntity(String tskId
			,String tskNm
			,String tskDtl
			,String tskCgryId
			,String tskExecFrcyId
			,String prtyId
			,LocalDateTime tskCompDttm
			,LocalDateTime rstrDttm
			,LocalDateTime updtDttm) {
		this.tskId = tskId;
		this.tskNm = tskNm;
		this.tskDtl = tskDtl;
		this.tskCgryId = tskCgryId;
		this.tskExecFrcyId = tskExecFrcyId;
		this.prtyId = prtyId;
		this.tskCompDttm = tskCompDttm;
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

	public String getTskCgryId() {
		return tskCgryId;
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

	@Override
	public int getType() {
		return TYPE_TASK;
	}
}
