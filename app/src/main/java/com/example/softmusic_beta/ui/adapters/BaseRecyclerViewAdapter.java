package com.example.softmusic_beta.ui.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softmusic_beta.ui.adapters.viewholders.BaseViewHolder;
import com.example.softmusic_beta.ui.adapters.models.BaseRecyclerViewItem;

import java.util.List;

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public enum ViewType {
        LIST
    }

    final List<BaseRecyclerViewItem> m_vItems;

    private ViewType m_vLayoutViewType;

    public BaseRecyclerViewAdapter(List<BaseRecyclerViewItem> items) { this.m_vItems = items;
    }

    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onInitializeView(this.m_vLayoutViewType);
        holder.onBindViewHolder(this.m_vItems.get(position));
    }

    public void setAdapterViewType(ViewType viewType) {
        this.m_vLayoutViewType = viewType;
    }

    @Override
    public int getItemCount() {
        return this.m_vItems.size();
    }

    public int getItemViewType(int position) {
        return this.m_vItems.get(position).getItemType().ordinal();
    }

    public ViewType getViewType() {
        return this.m_vLayoutViewType;
    }
}
