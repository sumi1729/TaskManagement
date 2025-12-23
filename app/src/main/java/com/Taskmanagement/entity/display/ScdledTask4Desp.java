package com.Taskmanagement.entity.display;

import androidx.annotation.NonNull;

import com.Taskmanagement.entity.item.ListItem;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScdledTask4Desp extends ListItem {

    public String tskId;
    public String tskNm;
    public String tskDtl;
    public String tskExecFrcyId;
    public String prtyId;
    public String tskCompDttm;
    public String reviewCgryId;
    public String reviewComment;
    public LocalDate tskExecDt;
    public LocalTime tskExecTm;
    public String scdlStat;
    public int dateSorter;
    public int timeSorter;


    public ScdledTask4Desp(
            String tskId
            , String tskNm
            , String tskDtl
            , String tskExecFrcyId
            , String prtyId
            , String tskCompDttm
            , String reviewCgryId
            , String reviewComment
            , LocalDate tskExecDt
            , LocalTime tskExecTm
            , String scdlStat
            , int dateSorter
            , int timeSorter
    ) {
        this.tskId = tskId;
        this.tskNm = tskNm;
        this.tskDtl = tskDtl;
        this.tskExecFrcyId = tskExecFrcyId;
        this.prtyId = prtyId;
        this.tskCompDttm = tskCompDttm;
        this.reviewCgryId = reviewCgryId;
        this.reviewComment = reviewComment;
        this.tskExecDt = tskExecDt;
        this.tskExecTm = tskExecTm;
        this.scdlStat = scdlStat;
        this.dateSorter = dateSorter;
        this.timeSorter = timeSorter;

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

    public String getPrtyId() {
        return prtyId;
    }

    public LocalDate getTskExecDt() {
        return tskExecDt;
    }

    public LocalTime getTskExecTm() {
        return tskExecTm;
    }

    @Override
    public int getType() {
        return TYPE_TASK;
    }

}
