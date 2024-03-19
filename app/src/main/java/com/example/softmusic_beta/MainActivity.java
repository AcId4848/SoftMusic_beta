package com.example.softmusic_beta;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.extensions.bottomsheet.CustomBottomSheetBehavior;
import com.example.softmusic_beta.ui.UIThread;
import com.example.softmusic_beta.views.panels.RootMediaPlayerPanel;
import com.example.softmusic_beta.views.panels.RootNavigationBarPanel;
import com.realgear.multislidinguppanel.Adapter;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;
import com.realgear.multislidinguppanel.PanelStateListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UIThread Thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        this.Thread = new UIThread(this);

    }
}