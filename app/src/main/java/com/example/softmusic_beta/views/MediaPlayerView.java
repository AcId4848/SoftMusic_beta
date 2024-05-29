package com.example.softmusic_beta.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.session.PlaybackState;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.example.mediaplayer.PlaybackManager;
import com.example.mediaplayer.interfaces.PlaybackCallback;
import com.example.mediaplayer.model.Song;
import com.example.mediaplayer.utils.PlaybackThread;
import com.example.softmusic_beta.R;
import com.example.softmusic_beta.ui.MediaPlayerManager;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;

public class MediaPlayerView implements PlaybackCallback {

    public static final int STATE_NORMAL = 0;

    public static final int STATE_PARTIAL = 1;

    private final View mRootView;

    private int mState;

    private FrameLayout mBottomSheet;
    private ConstraintLayout mControlsContainer;

    private ExtendedFloatingActionButton mExtFloatingActBtn_PlayPause_Big;

    private ExtendedFloatingActionButton mExtFloatingActBtn_PlayNext;

    private ExtendedFloatingActionButton mExtFloatingActBtn_PlayPrev;

    private TextView mTextView_SongArtist;

    private TextView mTextView_SongTitle;

    private TextView mTextView_SongDuration;

    private TextView mTextView_SongPosition;

    private ImageView mImageView_Art;

    private SeekBar mSeekBar;

    private Context mContext;

    private ConstraintLayout mBackgroundView;

    private PlaybackManager mPlaybackManager;

    private PlaybackState mPrevState;

    public MediaPlayerView(View rootView) {
        this.mRootView = rootView;

        this.mBackgroundView = findViewById(R.id.media_player_bg);
        this.mBottomSheet = findViewById(R.id.media_player_bottom_sheet_behavior);
        this.mControlsContainer = findViewById(R.id.media_player_controls_container);

        this.mExtFloatingActBtn_PlayPause_Big = findViewById(R.id.btn_play_pause_big);
        this.mExtFloatingActBtn_PlayNext = findViewById(R.id.btn_play_next);
        this.mExtFloatingActBtn_PlayPrev = findViewById(R.id.btn_play_previous);
        this.mImageView_Art = findViewById(R.id.media_player_album_art);

        this.mTextView_SongArtist = findViewById(R.id.text_view_song_artist_big);
        this.mTextView_SongTitle = findViewById(R.id.text_view_song_big);
        this.mTextView_SongDuration = findViewById(R.id.media_player_song_duration);
        this.mTextView_SongPosition = findViewById(R.id.media_player_song_position);

        this.mSeekBar = findViewById(R.id.media_player_seekbar);

        this.mRootView.setAlpha(0.0F);

        this.onInit();
    }

    private void onInit() {
        this.mExtFloatingActBtn_PlayPause_Big.setOnClickListener(v -> {
            MediaPlayerManager.getInstance().getCallback().onClickPlayPause();
        });

        this.mExtFloatingActBtn_PlayNext.setOnClickListener(v -> {
            MediaPlayerManager.getInstance().getCallback().onClickPlayNext();
        });

        this.mExtFloatingActBtn_PlayPrev.setOnClickListener(v -> {
            MediaPlayerManager.getInstance().getCallback().onClickPlayPrev();
        });
    }

    @SuppressLint("SetTextI18n")
    public void onMetadataChanged(MediaMetadata metadata) {

        long duration_minutes = metadata.getLong(MediaMetadata.METADATA_KEY_DURATION) / 60000;
        long duration_seconds = (metadata.getLong(MediaMetadata.METADATA_KEY_DURATION) -
                metadata.getLong(MediaMetadata.METADATA_KEY_DURATION) / 60000 * 60000) / 1000;
        this.mTextView_SongTitle.setText(metadata.getText(MediaMetadata.METADATA_KEY_TITLE));
        this.mTextView_SongArtist.setText(metadata.getText(MediaMetadata.METADATA_KEY_ARTIST));
//        this.mTextView_SongPosition.setText( / 1000);
        this.mTextView_SongDuration.setText(String.valueOf(duration_minutes) + ":"
                + String.valueOf((duration_seconds > 10 ? duration_seconds : "0"
                + String.valueOf(duration_seconds))));

        Bitmap album_art = metadata.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART);
        if (album_art != null) {
            this.mImageView_Art.setImageBitmap(album_art);
        } else {
            this.mImageView_Art.setImageDrawable(ResourcesCompat.getDrawable(this.mRootView.getResources(), com.example.icons_pack.R.drawable.audio_file_40px, this.mRootView.getContext().getTheme()));
        }

        this.mSeekBar.setMax((int) metadata.getLong(MediaMetadata.METADATA_KEY_DURATION));
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

    public void onPlaybackStateChanged(PlaybackState state) {
        this.mSeekBar.setProgress((int) state.getPosition());

        if (this.mPrevState == null || this.mPrevState.getState() != state.getState())
            this.mExtFloatingActBtn_PlayPause_Big.setIconResource((state.getState() == PlaybackState.STATE_PLAYING) ?
                    com.example.icons_pack.R.drawable.pause_40px :
                    com.example.icons_pack.R.drawable.play_arrow_40px);
    }

    @Override
    public void onUpdateMetadata(Song song) {

    }

    public void onUpdateVibrantDarkColor(int vibrantDarkColor) {
//        this.mBackgroundView.setBackgroundColor(vibrantDarkColor);
    }
}