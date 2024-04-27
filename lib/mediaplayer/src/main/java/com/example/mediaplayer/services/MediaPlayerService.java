package com.example.mediaplayer.services;

import android.app.Service;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.service.media.MediaBrowserService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mediaplayer.interfaces.PlaybackCallback;
import com.example.mediaplayer.interfaces.PlayerCallback;
import com.example.mediaplayer.model.Song;

import java.util.List;
import java.util.ListIterator;

public class MediaPlayerService extends MediaBrowserService implements PlaybackCallback {

    private final String TAG = this.getClass().getSimpleName();

    private MediaPlayerBroadcastHelper m_vBroadcastHelper;
    private final MediaSessionListener m_vCallback = new MediaSessionListener(this);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return new MediaBrowserService.BrowserRoot("media_root_id", null);
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowser.MediaItem>> result) {
        result.detach();
    }

    @Override
    public void onPlaybackStateChanged(PlaybackState playbackState) {

    }

    @Override
    public void onUpdateMetadata(Song song) {

    }

    public void onPlayPause() {

    }

    public void onPlay() {    }

    public void onPlayIndex(int index) {    }

    public void onPlay(List<Integer> queue, int index) { }

    public void onPause() {    }

    public void onPlayNext() { }

    public void onPlayPrev() { }

    public void onUpdateQueue(ListIterator<Integer> queue, int index) { }

    public void setRepeatState() { }

    public void setSeekbarPosition(int position) { }
}
