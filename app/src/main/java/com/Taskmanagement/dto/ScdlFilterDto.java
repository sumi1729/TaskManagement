package com.Taskmanagement.dto;

import java.time.LocalDate;

// 指定した日付・トグル状態を引き継いで
public class ScdlFilterDto {
    LocalDate date;
    boolean flg;
    public LocalDate getDate() {
        return date;
    }
    public boolean getIsAllTsk() {
        return flg;
    }
    public void setScdlFilter(LocalDate date, boolean flg) {
        this.date = date;
        this.flg = flg;
    }
}