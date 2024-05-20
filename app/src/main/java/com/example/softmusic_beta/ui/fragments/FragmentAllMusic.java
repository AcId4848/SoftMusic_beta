package com.example.softmusic_beta.ui.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediaplayer.LibraryManager;
import com.example.mediaplayer.model.Song;
import com.example.softmusic_beta.MainActivity;
import com.example.softmusic_beta.R;
import com.example.softmusic_beta.ui.adapters.BaseRecyclerViewAdapter;
import com.example.softmusic_beta.ui.adapters.LibraryRecyclerViewAdapter;
import com.example.softmusic_beta.ui.adapters.models.BaseRecyclerViewItem;
import com.example.softmusic_beta.ui.adapters.models.SongRecyclerViewItem;

import java.util.ArrayList;
import java.util.List;

public class FragmentAllMusic extends Fragment {

    private View m_vRootView;
    private SearchView m_vSearchView;
    private List<Song> m_vSongs;
    private MainActivity m_vMainActivity;

    private MenuItem m_vMenuItem;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.m_vRootView = inflater.inflate(R.layout.fragment_allmusic, container, false);



        return this.m_vRootView;
    }

    private RecyclerView m_vLibraryRecyclerView;
    private BaseRecyclerViewAdapter m_vLibraryAdapter;

    private GridLayoutManager m_vGridLayout;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.m_vLibraryRecyclerView = findViewById(R.id.library_recyclerView);

        int panelheights = getResources().getDimensionPixelSize(R.dimen.navigation_bar_height) + getResources().getDimensionPixelSize(R.dimen.mediaplayerbar_height);
        this.m_vLibraryRecyclerView.setPadding(0, 0, 0, panelheights);
        this.m_vSongs = LibraryManager.getSongs(getContext());

        List<BaseRecyclerViewItem> items = new ArrayList<>();

        for (Song song : m_vSongs) {
            items.add(new SongRecyclerViewItem(song));
        }

        this.m_vLibraryAdapter = new LibraryRecyclerViewAdapter(items);
        this.m_vLibraryAdapter.setHasStableIds(true);
        setAdapterViewType(BaseRecyclerViewAdapter.ViewType.LIST);
        this.m_vLibraryRecyclerView.setAdapter(this.m_vLibraryAdapter);

        ImageButton btnView = findViewById(R.id.btn_grid_or_list_allmusic);
        btnView.setOnClickListener(v -> {
            setAdapterViewType((this.m_vLibraryAdapter.getViewType() == BaseRecyclerViewAdapter.ViewType.GRID) ? BaseRecyclerViewAdapter.ViewType.LIST : BaseRecyclerViewAdapter.ViewType.GRID);
        });

        ImageButton btnSettings = findViewById(R.id.btn_settings_allmusic);
        btnSettings.setOnClickListener(v -> {

        });



    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

        @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item, menu);
        m_vMenuItem = menu.findItem(R.menu.menu_item);
        m_vSearchView = (SearchView) MenuItemCompat.getActionView(m_vMenuItem);
        m_vSearchView.setIconified(true);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        m_vSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        m_vSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterList(query, m_vSongs);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void filterList(String text, List<Song> songs) {
        List<BaseRecyclerViewItem> filteredList = new ArrayList<>();

        for (Song song : songs) {
            if (song.getArtistName().toLowerCase().contains(text.toLowerCase()) ||
                    song.getAlbumName().toLowerCase().contains(text.toLowerCase()) ||
                    song.getTitle().toLowerCase().contains(text.toLowerCase())) {
                        filteredList.add(new SongRecyclerViewItem(song));
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(m_vMainActivity, "Песня не найдена :/", Toast.LENGTH_SHORT).show();
        } else {
            this.m_vLibraryAdapter = new LibraryRecyclerViewAdapter(filteredList);
            this.m_vLibraryRecyclerView.setAdapter(m_vLibraryAdapter);
        }
    }

    private void setAdapterViewType(BaseRecyclerViewAdapter.ViewType viewType) {
        this.m_vLibraryAdapter.setAdapterViewType(viewType);

        int rowCount = (viewType == BaseRecyclerViewAdapter.ViewType.LIST) ? 1 : 3;

        if (m_vGridLayout == null) {
            this.m_vGridLayout = new GridLayoutManager(getContext(), rowCount);
            this.m_vLibraryRecyclerView.setLayoutManager(this.m_vGridLayout);
        } else {
            this.m_vGridLayout.setSpanCount(rowCount);
            this.m_vLibraryAdapter.notifyDataSetChanged();
        }
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return this.m_vRootView.findViewById(id);
    }

//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        filterList(newText, m_vSongs);
//        return true;
//    }
}
