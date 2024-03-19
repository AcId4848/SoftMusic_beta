package com.example.softmusic_beta.ui.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softmusic_beta.ui.adapters.viewholders.BaseViewHolder;
import com.example.softmusic_beta.ui.adapters.models.BaseRecyclerViewItem;

import java.util.List;

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    final List<BaseRecyclerViewItem> Items;

    public BaseRecyclerViewAdapter(List<BaseRecyclerViewItem> items) {
        Items = items;
    }

    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(this.Items.get(position));
    }

    @Override
    public int getItemCount() {
        return this.Items.size();
    }

    public int getItemViewType(int position) {
        return this.Items.get(position).getItemType().ordinal();
    }
}
