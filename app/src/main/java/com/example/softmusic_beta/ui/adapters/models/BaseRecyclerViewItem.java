package com.example.softmusic_beta.ui.adapters.models;

public abstract class BaseRecyclerViewItem {
    public enum ItemType {
        SONG
    }

    private final String Title;
    private final ItemType ItemType;

    public BaseRecyclerViewItem(String title, ItemType itemType) {
        this.Title = title;
        this.ItemType = itemType;
    }

    public String getTitle() {
        return this.Title;
    }

    public ItemType getItemType() {
        return this.ItemType;
    }
}
