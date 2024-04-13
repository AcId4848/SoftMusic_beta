package com.example.softmusic_beta.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class StateFragmentAdapter extends FragmentStateAdapter {

    private final List<Fragment> m_vItems;

    private final FragmentManager m_vFragmentManager;


    public StateFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);

        this.m_vItems = new ArrayList<>();
        this.m_vFragmentManager = fragmentManager;
    }

    public void addFragment(Fragment fragment) {
        this.m_vItems.add(fragment);
    }

    public Fragment getFragment(int position) {
        return this.m_vItems.get(position);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return this.m_vItems.get(position);
    }

    @Override
    public int getItemCount() {
        return this.m_vItems.size();
    }
}
