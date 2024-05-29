package com.example.softmusic_beta.ui.adapters;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.softmusic_beta.ui.UIManager;
import com.example.softmusic_beta.ui.adapters.helpers.BaseViewHelper;
import com.example.softmusic_beta.ui.adapters.models.BaseRecyclerViewItem;
import com.example.softmusic_beta.ui.adapters.models.SongRecyclerViewItem;
import com.example.softmusic_beta.ui.adapters.viewholders.BaseViewHolder;
import com.example.softmusic_beta.ui.adapters.viewholders.SongViewHolder;

import java.util.ArrayList;
import java.util.List;

public class LibraryRecyclerViewAdapter extends BaseRecyclerViewAdapter{
    public LibraryRecyclerViewAdapter(List<BaseRecyclerViewItem> items) {
        super(items);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BaseRecyclerViewItem.ItemType itemType = BaseRecyclerViewItem.ItemType.values()[viewType];

        switch (itemType) {
            case SONG:
                return BaseViewHelper.onCreateViewHolder(SongViewHolder.class, parent);

            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        holder.itemView.setOnClickListener(v -> {
            int i = position;
            UIManager.getInstance().getMediaPlayerManager().getCallback().onClickPlay(i, getQueue());
        });
    }

    private List<Integer> getQueue() {
        List<Integer> results = new ArrayList<>();
        if (this.m_vItems != null) {
            for (int i = 0; i < this.m_vItems.size(); i++) {
                results.add((int)((SongRecyclerViewItem)this.m_vItems.get(i)).getSongId());
            }
        }

        return results;
    }
}
