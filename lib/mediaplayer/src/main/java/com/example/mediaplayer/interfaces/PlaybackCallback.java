package com.example.mediaplayer.interfaces;

import android.media.session.PlaybackState;

import com.example.mediaplayer.model.Song;

public interface PlaybackCallback {
    void onPlaybackStateChanged (PlaybackState playbackState);
    void onUpdateMetadata (Song song);
}
