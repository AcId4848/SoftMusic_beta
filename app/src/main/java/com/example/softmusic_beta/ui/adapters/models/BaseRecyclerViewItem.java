package com.example.softmusic_beta.ui.adapters.models;

public abstract class BaseRecyclerViewItem {
    public abstract int getHashCode();

    public enum ItemType {
        SONG
    }

    private final String m_vTitle;
    private final ItemType m_vItemType;

    private final String m_vArtistName;

    private final long m_vDuration;

    public BaseRecyclerViewItem(String title, String artist, long duration, ItemType itemType) {
        this.m_vTitle = title;
        this.m_vArtistName = artist;
        this.m_vDuration = duration;
        this.m_vItemType = itemType;
    }

    public long getDuration() { return this.m_vDuration; }

    public String getTitle() {
        return this.m_vTitle;
    }

    public String getArtistName() { return this.m_vArtistName; }

    public ItemType getItemType() {
        return this.m_vItemType;
    }
}
