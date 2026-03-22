package com.Taskmanagement.entity.item;

import java.util.List;

public class MainItem {
    public String mainName;
    public List<String> subNameList;
    public boolean isExpanded;

    public MainItem(String mainName, List<String> subNameList, boolean isExpanded) {
        this.mainName = mainName;
        this.subNameList = subNameList;
        this.isExpanded = isExpanded;
    }
}