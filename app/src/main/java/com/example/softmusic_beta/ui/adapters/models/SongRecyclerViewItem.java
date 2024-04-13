package com.example.softmusic_beta.ui.adapters.models;

import com.example.mediaplayer.model.Song;

public class SongRecyclerViewItem extends BaseRecyclerViewItem {

    private Song m_vItem;

    public SongRecyclerViewItem(Song song) {
        super(song.getTitle(), ItemType.SONG);

        this.m_vItem = song;
    }

    public String getFilePath() {
        return this.m_vItem.getData();
    }


}
