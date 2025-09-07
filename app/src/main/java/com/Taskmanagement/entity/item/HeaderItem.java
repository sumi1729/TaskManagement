package com.Taskmanagement.entity.item;

public class HeaderItem extends ListItem {
    public String title;

    public HeaderItem(String title) {
        this.title = title;
    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }

    public String getTitle() {
        return title;
    }
}
