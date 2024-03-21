package com.example.softmusic_beta.ui.adapters.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.softmusic_beta.R;
import com.example.softmusic_beta.glide.audiocover.AudioFileCover;
import com.example.softmusic_beta.ui.adapters.models.BaseRecyclerViewItem;
import com.example.softmusic_beta.ui.adapters.models.SongRecyclerViewItem;

public class SongViewHolder extends BaseViewHolder {

    public SongViewHolder(@NonNull View itemView, BaseViewHolder.ViewType viewType) {
        super(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewItem viewItem) {
        SongRecyclerViewItem item = (SongRecyclerViewItem) viewItem;

        TextView title = findViewById(R.id.item_song_title);
        ImageView imageView = findViewById(R.id.item_song_art);
        TextView artist = findViewById(R.id.item_artist);
        TextView duration = findViewById(R.id.item_song_duration);

        title.setText(viewItem.getTitle());
        Glide.with(itemView.getContext())
                .load(new AudioFileCover(item.getFilePath()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }
}
