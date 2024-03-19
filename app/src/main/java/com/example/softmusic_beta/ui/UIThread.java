package com.example.softmusic_beta.ui;

import androidx.annotation.IdRes;

import com.example.softmusic_beta.MainActivity;
import com.example.softmusic_beta.R;
import com.example.softmusic_beta.views.panels.RootMediaPlayerPanel;
import com.example.softmusic_beta.views.panels.RootNavigationBarPanel;
import com.realgear.multislidinguppanel.Adapter;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;
import com.realgear.multislidinguppanel.PanelStateListener;

import java.util.ArrayList;
import java.util.List;

public class UIThread {

    private final MainActivity MainActivity;

    private MultiSlidingUpPanelLayout MultiSlidingPanel;

    public UIThread(MainActivity activity) {
        this.MainActivity = activity;

        onCreate();
    }

    public void onCreate() {
        MultiSlidingUpPanelLayout panelLayout = findViewById(R.id.root_sliding_up_panel);

        List<Class<?>> items = new ArrayList<>();


        items.add(RootMediaPlayerPanel.class);
        items.add(RootNavigationBarPanel.class);

        panelLayout.setPanelStateListener(new PanelStateListener(panelLayout) {});

        panelLayout.setAdapter(new Adapter(this.MainActivity, items));
    }

    public <T extends android.view.View> T findViewById(@IdRes int id) {
        return this.MainActivity.findViewById(id);

    }
}
