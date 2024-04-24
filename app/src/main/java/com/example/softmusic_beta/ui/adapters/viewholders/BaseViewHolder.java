package com.example.softmusic_beta.ui.adapters.viewholders;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softmusic_beta.ui.adapters.BaseRecyclerViewAdapter;
import com.example.softmusic_beta.ui.adapters.models.BaseRecyclerViewItem;

public abstract class  BaseViewHolder extends RecyclerView.ViewHolder {


    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void onBindViewHolder(BaseRecyclerViewItem viewItem);

    public abstract void onInitializeView(BaseRecyclerViewAdapter.ViewType viewType);

    public <T extends android.view.View> T findViewById(@IdRes int id) {
        return this.itemView.findViewById(id);

    }
}
