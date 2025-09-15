package com.Taskmanagement.entity.display;

import androidx.annotation.NonNull;

import com.Taskmanagement.entity.item.ListItem;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScdledTask4Desp extends ListItem {

    public String tskId;
    public String tskNm;
    public String tskDtl;
    public String tskCgryId;
    public String tskExecFrcyId;
    public String prtyId;
    public String tskCompDttm;
    public LocalDate tskExecDt;
    public LocalTime tskExecTm;
    public String scdlStat;


    public ScdledTask4Desp(
            String tskId
            , String tskNm
            , String tskDtl
            , String tskCgryId
            , String tskExecFrcyId
            , String prtyId
            , String tskCompDttm
            , LocalDate tskExecDt
            , LocalTime tskExecTm
            , String scdlStat
    ) {
        this.tskId = tskId;
        this.tskNm = tskNm;
        this.tskDtl = tskDtl;
        this.tskCgryId = tskCgryId;
        this.tskExecFrcyId = tskExecFrcyId;
        this.prtyId = prtyId;
        this.tskCompDttm = tskCompDttm;
        this.tskExecDt = tskExecDt;
        this.tskExecTm = tskExecTm;
        this.scdlStat = scdlStat;

    }

    @NonNull
    public String getTskId() {
        return tskId;
    }

    @Override
    public int getType() {
        return TYPE_TASK;
    }

}
