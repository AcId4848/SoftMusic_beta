package com.example.softmusic_beta.views;

import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.IdRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.example.softmusic_beta.R;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;

public class MediaPlayerView {

    public static final int STATE_NORMAL = 0;

    public static final int STATE_PARTIAL = 1;

    private final View mRootView;

    private int mState;

    private FrameLayout mBottomSheet;
    private ConstraintLayout mControlsContainer;

    private ImageView mImageView_Art;

    public MediaPlayerView(View rootView) {
        this.mRootView = rootView;

        this.mBottomSheet = findViewById(R.id.media_player_bottom_sheet_behavior);
        this.mControlsContainer = findViewById(R.id.media_player_controls_container);

        this.mImageView_Art = findViewById(R.id.media_player_album_art);

        this.mRootView.setAlpha(0.0F);
    }

    public void onPlayerMetadataChanged(MediaMetadata metadata) {
        Bitmap album_art = metadata.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART);
        if (album_art != null) {
            this.mImageView_Art.setImageBitmap(album_art);
        } else {
            this.mImageView_Art.setImageDrawable(ResourcesCompat.getDrawable(this.mRootView.getResources(), com.example.icons_pack.R.drawable.audio_file_40px, this.mRootView.getContext().getTheme()));
        }
    }

    public void onSliding(float slideOffset, int state) {
        float fadeStart = 0.25F;
        float alpha = (slideOffset - fadeStart) * (1F / (1F - fadeStart));

        if (state == STATE_NORMAL) {
            this.mRootView.setAlpha(alpha);
            this.mControlsContainer.setAlpha(1F);
        } else {
            this.mControlsContainer.setAlpha(1F - alpha);
        }

        this.mState = state;
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return this.mRootView.findViewById(id);
    }

    public void onPanelStateChanged(int panelState) {
        if (panelState == MultiSlidingUpPanelLayout.COLLAPSED) {
            this.mRootView.setVisibility(View.INVISIBLE);
        } else {
            this.mRootView.setVisibility(View.VISIBLE);
        }
    }
}
