package com.example.softmusic_beta.views;

import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.media.MediaMetadata;
import android.media.session.PlaybackState;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.example.softmusic_beta.R;
import com.example.softmusic_beta.ui.MediaPlayerManager;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class MediaPlayerBarView {
    public static final int STATE_NORMAL = 0;

    public static final int STATE_PARTIAL = 1;

    private final View mRootView;

    private int mState;

    private FrameLayout mBackgroundView;
    private LinearProgressIndicator mProgressIndicator;
    private ConstraintLayout mControlsContainer;

    private ImageView mImageView_Art;
    private TextView mTextView_SongTitle;
    private TextView mTextView_SongArtist;
    private ImageButton mImageBtn_PlayPause;

    private PlaybackState mPrevState;

    public MediaPlayerBarView(View rootView) {
        this.mRootView = rootView;

        this.mBackgroundView = findViewById(R.id.media_player_bar_bg);
        this.mControlsContainer = findViewById(R.id.media_player_controls_container);
        this.mProgressIndicator = findViewById(R.id.media_player_bar_progress_indicator);

        this.mImageView_Art = findViewById(R.id.image_view_music_file_image_of_album);
        this.mImageBtn_PlayPause = findViewById(R.id.btn_play_pause);

        this.mTextView_SongTitle = findViewById(R.id.text_view_song);
        this.mTextView_SongArtist = findViewById(R.id.text_view_song_artist);

        this.mRootView.setAlpha(1.0F);

        this.onInit();
    }

    private void onInit() {
        this.mImageBtn_PlayPause.setOnClickListener(v -> {
            MediaPlayerManager.getInstance().getCallback().onClickPlayPause();
        });
    }

    public void onSliding(float slideOffset, int state) {
        float fadeStart = 0.25F;
        float alpha = (slideOffset - fadeStart);

        if (state == STATE_NORMAL) {
            this.mRootView.setAlpha(1F - alpha);
            this.mBackgroundView.setAlpha(1F);
            this.mProgressIndicator.setAlpha(1F);
            this.mControlsContainer.setAlpha(1F);
        } else {
            this.mRootView.setAlpha(alpha);
            this.mBackgroundView.setAlpha(0F);
            this.mProgressIndicator.setAlpha(0F);
            this.mControlsContainer.setAlpha(1F);
        }

        this.mState = state;
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return this.mRootView.findViewById(id);
    }

    public void onPlaybackStateChanged(PlaybackState state) {
        this.mProgressIndicator.setProgress((int) state.getPosition());

        if (this.mPrevState == null || this.mPrevState.getState() != state.getState())
            this.mImageBtn_PlayPause.setImageIcon(Icon.createWithResource(this.mRootView.getContext(), (state.getState() == PlaybackState.STATE_PLAYING) ?
                    com.example.icons_pack.R.drawable.pause_40px :
                    com.example.icons_pack.R.drawable.play_arrow_40px));
    }

    public void onMetadataChanged(MediaMetadata metadata) {
        this.mTextView_SongTitle.setText(metadata.getText(MediaMetadata.METADATA_KEY_TITLE));
        this.mTextView_SongArtist.setText(metadata.getText(MediaMetadata.METADATA_KEY_ARTIST));


        Bitmap album_art = metadata.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART);
        if (album_art != null) {
            this.mImageView_Art.setImageBitmap(album_art);
        } else {
            this.mImageView_Art.setImageDrawable(ResourcesCompat.getDrawable(this.mRootView.getResources(), com.example.icons_pack.R.drawable.audio_file_40px, this.mRootView.getContext().getTheme()));
        }

        this.mProgressIndicator.setMax((int) metadata.getLong(MediaMetadata.METADATA_KEY_DURATION));
    }

    public void onPanelStateChanged(int panelState) {

    }

    public void onUpdateVibrantColor(int vibrantColor) {
    }

    public void onUpdateVibrantDarkColor(int vibrantDarkColor) {
        this.mBackgroundView.setBackgroundColor(vibrantDarkColor);
    }

    public void onUpdateVibrantLightColor(int vibrantLightColor) {
    }
}
