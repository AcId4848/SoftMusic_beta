package com.example.softmusic_beta.ui.adapters.viewholders;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.softmusic_beta.ui.adapters.models.BaseRecyclerViewItem;
import com.example.softmusic_beta.ui.adapters.models.SongRecyclerViewItem;

public class SongViewHolder extends BaseViewHolder {

    public SongViewHolder(@NonNull View itemView, BaseViewHolder.ViewType viewType) {
        super(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewItem viewItem) {
        SongRecyclerViewItem item = (SongRecyclerViewItem) viewItem;
    }
}
