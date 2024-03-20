package com.example.softmusic_beta.ui.adapters.models;

import com.example.mediaplayer.model.Song;

public class SongRecyclerViewItem extends BaseRecyclerViewItem {

    private Song Item;

    public SongRecyclerViewItem(Song song) {
        super(song.getTitle(), ItemType.SONG);

        this.Item = song;
    }

    public String getFilePath() {
        return this.Item.getData();
    }


}
