package com.example.softmusic_beta.ui.adapters.viewholders;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softmusic_beta.ui.adapters.models.BaseRecyclerViewItem;

public abstract class  BaseViewHolder extends RecyclerView.ViewHolder {

    public enum ViewType {
        LIST
    }

    private final ViewType m_vViewType;

    public BaseViewHolder(@NonNull View itemView, ViewType viewType) {
        super(itemView);

        this.m_vViewType = viewType;
    }

    public abstract void onBindViewHolder(BaseRecyclerViewItem viewItem);

    public <T extends android.view.View> T findViewById(@IdRes int id) {
        return this.itemView.findViewById(id);

    }
}
