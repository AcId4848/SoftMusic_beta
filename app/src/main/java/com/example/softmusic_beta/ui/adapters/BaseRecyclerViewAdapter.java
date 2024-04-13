package com.example.softmusic_beta.ui.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softmusic_beta.ui.adapters.viewholders.BaseViewHolder;
import com.example.softmusic_beta.ui.adapters.models.BaseRecyclerViewItem;

import java.util.List;

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    final List<BaseRecyclerViewItem> m_vItems;

    public BaseRecyclerViewAdapter(List<BaseRecyclerViewItem> items) { this.m_vItems = items;
    }

    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(this.m_vItems.get(position));
    }

    @Override
    public int getItemCount() {
        return this.m_vItems.size();
    }

    public int getItemViewType(int position) {
        return this.m_vItems.get(position).getItemType().ordinal();
    }
}
