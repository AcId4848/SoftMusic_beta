package com.example.softmusic_beta.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediaplayer.LibraryManager;
import com.example.mediaplayer.model.Song;
import com.example.softmusic_beta.R;
import com.example.softmusic_beta.ui.adapters.BaseRecyclerViewAdapter;
import com.example.softmusic_beta.ui.adapters.LibraryRecyclerViewAdapter;
import com.example.softmusic_beta.ui.adapters.models.BaseRecyclerViewItem;
import com.example.softmusic_beta.ui.adapters.models.SongRecyclerViewItem;

import java.util.ArrayList;
import java.util.List;

public class FragmentAllMusic extends Fragment {

    private View m_vRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.m_vRootView = inflater.inflate(R.layout.fragment_allmusic, container, false);



        return this.m_vRootView;
    }

    private RecyclerView m_vLibraryRecyclerView;
    private BaseRecyclerViewAdapter m_vLibraryAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.m_vLibraryRecyclerView = findViewById(R.id.library_recyclerView);

        int panelheights = getResources().getDimensionPixelSize(R.dimen.navigation_bar_height) + getResources().getDimensionPixelSize(R.dimen.mediaplayerbar_height);
        this.m_vLibraryRecyclerView.setPadding(0, 0, 0, panelheights);

        List<Song> songs = LibraryManager.getSongs(getContext());

        List<BaseRecyclerViewItem> items = new ArrayList<>();

        for (Song song : songs) {
            items.add(new SongRecyclerViewItem(song));
        }

        this.m_vLibraryAdapter = new LibraryRecyclerViewAdapter(items);
        this.m_vLibraryAdapter.setAdapterViewType(BaseRecyclerViewAdapter.ViewType.LIST);
        this.m_vLibraryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.m_vLibraryRecyclerView.setAdapter(this.m_vLibraryAdapter);
    }

    private void setAdapterViewType(BaseRecyclerViewAdapter.ViewType viewType) {
        this.m_vLibraryAdapter.setAdapterViewType(viewType);

        int rowCount = (viewType == BaseRecyclerViewAdapter.ViewType.LIST) ? 1 : 3;

    }

    public <T extends View> T findViewById(@IdRes int id) {
        return this.m_vRootView.findViewById(id);
    }
}
