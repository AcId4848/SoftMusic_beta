package com.example.softmusic_beta.views.panels;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.example.softmusic_beta.R;
import com.example.softmusic_beta.ui.adapters.StateFragmentAdapter;
import com.example.softmusic_beta.ui.fragments.FragmentAllMusic;
import com.example.softmusic_beta.ui.fragments.FragmentHome;
import com.example.softmusic_beta.ui.fragments.FragmentLibrary;
import com.realgear.multislidinguppanel.BasePanelView;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;
import com.realgear.readable_bottom_bar.ReadableBottomBar;

public class RootNavigationBarPanel extends BasePanelView {

    private ViewPager2 rootViewPager;
    private ReadableBottomBar rootNavigationBar;
    public RootNavigationBarPanel(@NonNull Context context, MultiSlidingUpPanelLayout panelLayout) {
        super(context, panelLayout);

        getContext().setTheme(R.style.Theme_SoftMusic_beta);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_root_navigation_bar, this, true);
    }

    @Override
    public void onCreateView() {
        this.setPanelState(MultiSlidingUpPanelLayout.COLLAPSED);
        this.setSlideDirection(MultiSlidingUpPanelLayout.SLIDE_VERTICAL);

        this.setPeakHeight(getResources().getDimensionPixelSize(R.dimen.navigation_bar_height));
    }

    @Override
    public void onBindView() {
        rootViewPager = getMultiSlidingUpPanel().findViewById(R.id.root_view_pager);
        rootNavigationBar = findViewById(R.id.root_navigation_bar);

        StateFragmentAdapter adapter = new StateFragmentAdapter(getSupportFragmentManager(), getLifecycle());

        adapter.addFragment(new FragmentHome());
        adapter.addFragment(new FragmentLibrary());
        adapter.addFragment(FragmentAllMusic.class);

        rootViewPager.setAdapter(adapter);
        rootNavigationBar.setupWithViewPager2(rootViewPager);
    }

    @Override
    public void onPanelStateChanged(int i) {

    }
}
