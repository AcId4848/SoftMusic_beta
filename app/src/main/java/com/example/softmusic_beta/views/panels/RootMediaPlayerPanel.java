package com.example.softmusic_beta.views.panels;

import android.content.Context;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.media.session.PlaybackState;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.example.extensions.bottomsheet.CustomBottomSheetBehavior;
import com.example.softmusic_beta.R;
import com.example.softmusic_beta.theme.AsyncPaletteBuilder;
import com.example.softmusic_beta.theme.interfaces.PaletteStateListener;
import com.example.softmusic_beta.ui.adapters.StateFragmentAdapter;
import com.example.softmusic_beta.ui.fragments.FragmentAllMusic;
import com.example.softmusic_beta.ui.fragments.FragmentHome;
import com.example.softmusic_beta.ui.fragments.FragmentLibrary;
import com.example.softmusic_beta.views.MediaPlayerBarView;
import com.example.softmusic_beta.views.MediaPlayerView;
import com.realgear.multislidinguppanel.BasePanelView;
import com.realgear.multislidinguppanel.Adapter;
import com.realgear.multislidinguppanel.IPanel;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;
import com.realgear.readable_bottom_bar.ReadableBottomBar;

public class RootMediaPlayerPanel extends BasePanelView implements PaletteStateListener {

    private ViewPager2 rootViewPager;
    private ReadableBottomBar rootNavigationBar;

    private MediaPlayerView mMediaPlayerView;

    private MediaPlayerBarView mMediaPlayerBarView;

    private AsyncPaletteBuilder mAsyncPaletteBuilder;

    public RootMediaPlayerPanel(@NonNull Context context, MultiSlidingUpPanelLayout panelLayout) {
        super(context, panelLayout);

        getContext().setTheme(R.style.Theme_SoftMusic_beta);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_root_mediaplayer, this, true);

        this.mAsyncPaletteBuilder = new AsyncPaletteBuilder(this.getContext(), this);
    }

    @Override
    public void onCreateView() {
        this.setPanelState(MultiSlidingUpPanelLayout.HIDDEN );
        this.setSlideDirection(MultiSlidingUpPanelLayout.SLIDE_VERTICAL);

        this.setPeakHeight(getResources().getDimensionPixelSize(R.dimen.mediaplayerbar_height));
    }

    @Override
    public void onBindView() {
        mMediaPlayerView = new MediaPlayerView(findViewById(R.id.media_player_view));
        mMediaPlayerBarView = new MediaPlayerBarView(findViewById(R.id.media_player_bar_view));

        DisplayMetrics dm = getResources().getDisplayMetrics();
        FrameLayout layout = findViewById(R.id.media_player_bottom_sheet_behavior);

        ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.height = dm.heightPixels - (mPeakHeight);
        layout.setLayoutParams(params);

        CustomBottomSheetBehavior<FrameLayout> bottomSheetBehavior = CustomBottomSheetBehavior.from(layout);
        bottomSheetBehavior.setSkipAnchored(false);
        bottomSheetBehavior.setAllowUserDragging(true);

        bottomSheetBehavior.setAnchorOffset((int)(dm.heightPixels * 0.75F));
        bottomSheetBehavior.setPeekHeight(getPeakHeight());
        bottomSheetBehavior.setMediaPlayerBarHeight(getPeakHeight());
        bottomSheetBehavior.setState(CustomBottomSheetBehavior.STATE_COLLAPSED);

        bottomSheetBehavior.addBottomSheetCallback(new CustomBottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int oldState, int newState) {
                switch (newState) {
                    case CustomBottomSheetBehavior.STATE_ANCHORED:
                    case CustomBottomSheetBehavior.STATE_EXPANDED:
                    case CustomBottomSheetBehavior.STATE_DRAGGING:
                        getMultiSlidingUpPanel().setSlidingEnabled(false);
                        break;

                    default:
                        getMultiSlidingUpPanel().setSlidingEnabled(true);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                mMediaPlayerView.onSliding(slideOffset, MediaPlayerView.STATE_PARTIAL);
                mMediaPlayerBarView.onSliding(slideOffset, MediaPlayerBarView.STATE_PARTIAL);
            }
        });

    }

    @Override
    public void onPanelStateChanged(int panelState) {
        if (this.mMediaPlayerBarView != null)
            this.mMediaPlayerBarView.onPanelStateChanged(panelState);
        if (this.mMediaPlayerView != null)
            this.mMediaPlayerView.onPanelStateChanged(panelState);
    }

    @Override
    public void onSliding(@NonNull IPanel<View> panel, int top, int dy, float slidingOffset) {
        super.onSliding(panel, top, dy, slidingOffset);

        mMediaPlayerView.onSliding(slidingOffset, MediaPlayerView.STATE_NORMAL);
        mMediaPlayerBarView.onSliding(slidingOffset, MediaPlayerBarView.STATE_NORMAL);
    }

    public void onMetadataChanged(MediaMetadata metadata) {
        this.mMediaPlayerBarView.onMetadataChanged(metadata);

        Bitmap bitmap = metadata.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART);
        this.mAsyncPaletteBuilder.onStartAnimation(bitmap);
    }

    public void onPlaybackStateChanged(PlaybackState state) {
        if (state.getState() == PlaybackState.STATE_PLAYING || state.getState() == PlaybackState.STATE_PAUSED) {
            if (this.getPanelState() == MultiSlidingUpPanelLayout.HIDDEN)
                this.collapsePanel();
        }

        this.mMediaPlayerBarView.onPlaybackStateChanged(state);
    }

    @Override
    public void onUpdateVibrantColor(int vibrantColor) {
        this.mMediaPlayerBarView.onUpdateVibrantColor(vibrantColor);
    }

    @Override
    public void onUpdateVibrantDarkColor(int vibrantDarkColor) {
        this.mMediaPlayerBarView.onUpdateVibrantDarkColor(vibrantDarkColor);
    }

    @Override
    public void onUpdateVibrantLightColor(int vibrantLightColor) {
        this.mMediaPlayerBarView.onUpdateVibrantLightColor(vibrantLightColor);
    }
}
