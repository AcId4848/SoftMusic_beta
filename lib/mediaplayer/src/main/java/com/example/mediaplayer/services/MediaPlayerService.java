package com.example.mediaplayer.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.service.media.MediaBrowserService;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mediaplayer.LibraryManager;
import com.example.mediaplayer.MediaNotificationManager;
import com.example.mediaplayer.PlaybackManager;
import com.example.mediaplayer.interfaces.PlaybackCallback;
import com.example.mediaplayer.interfaces.PlayerCallback;
import com.example.mediaplayer.model.Song;

import java.util.List;
import java.util.ListIterator;

public class MediaPlayerService extends MediaBrowserService implements PlaybackCallback {

    private final String TAG = this.getClass().getSimpleName();

    private MediaPlayerBroadcastHelper m_vBroadcastHelper;
    private final MediaSessionListener m_vCallback = new MediaSessionListener(this);

    private Context m_vContext;
    private int m_vCurrentIndex;
    private MediaSession m_vMediaSession;
    private MediaNotificationManager m_vNotificationManager;
    private PlaybackManager m_vPlaybackManager;
    private Song m_vPrevSong;
    private PlaybackState m_vPrevState;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.m_vContext = getApplicationContext();

        this.m_vMediaSession = new MediaSession(this.m_vContext, TAG);
        this.m_vMediaSession.setCallback(this.m_vCallback);

        this.setSessionToken(this.m_vMediaSession.getSessionToken());

        this.m_vNotificationManager = new MediaNotificationManager(this);
        this.m_vPlaybackManager = new PlaybackManager(this.m_vContext, this);
        this.m_vBroadcastHelper = new MediaPlayerBroadcastHelper(this);

        this.m_vBroadcastHelper.registerReceivers();
    }

    @Override
    public void onDestroy() {
        this.m_vPlaybackManager.onStop();
        this.m_vMediaSession.release();
        this.m_vBroadcastHelper.unregisterReceivers();
        this.m_vNotificationManager.onStop();

        super.onDestroy();
    }

    public MediaNotificationManager getNotificationManager() { return this.m_vNotificationManager; }
    public PlaybackManager getPlaybackManager() { return this.m_vPlaybackManager; }

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
        this.m_vPrevSong = (this.m_vPrevSong == null) ? this.m_vPlaybackManager.getCurrentSong() : this.m_vPrevSong;

        /*if (this.m_vPrevState == null) {
            this.m_vPrevState = playbackState;
        } else if (this.m_vPrevState.getState() == playbackState.getState() && this.m_vPrevSong.getId() == this.m_vPlaybackManager.getCurrentSong().getId()) {
            this.m_vMediaSession.setPlaybackState(playbackState);
            return;
        }*/

        this.m_vMediaSession.setPlaybackState(playbackState);
        this.m_vNotificationManager.onUpdateNotification(
                LibraryManager.getMediaMetadata(this.m_vPlaybackManager.getCurrentSong()),
                playbackState,
                this.m_vMediaSession.getSessionToken());

        this.m_vPrevSong = this.m_vPlaybackManager.getCurrentSong();
        this.m_vPrevState = playbackState;
        this.m_vCurrentIndex = this.m_vPlaybackManager.getCurrentQueueIndex();
    }

    @Override
    public void onUpdateMetadata(Song song) {

        this.m_vMediaSession.setMetadata(LibraryManager.getMediaMetadata(song));
    }

    public void onPlayPause() {
        this.m_vPlaybackManager.onPlayPause();
    }

    public void onPlay() {
        this.m_vPlaybackManager.onPlayIndex(this.m_vCurrentIndex);
    }

    public void onPlayIndex(int index) {
        this.m_vPlaybackManager.onPlayIndex(index);
    }

    // TODO: complete this function
    public void onFavourite() { this.m_vPlaybackManager.onFavourite();}

    public void onPlay(List<Integer> queue, int index) {
        this.m_vPlaybackManager.onSetQueue(queue);
        this.m_vPlaybackManager.onPlayIndex(index);
        this.m_vCurrentIndex = index;
    }

    public void onPause() {
        this.m_vPlaybackManager.onPause();
    }

    public void onPlayNext() {
        this.m_vPlaybackManager.onPlayNext();
    }

    public void onPlayPrev() {
        this.m_vPlaybackManager.onPlayPrevious();
    }

    public void onUpdateQueue(List<Integer> queue, int index) {
        this.m_vPlaybackManager.onSetQueue(queue);
//        this.m_vPlaybackManager.onPlayIndex(index);
        this.m_vCurrentIndex= index;

    }

    public void setRepeatState(@PlaybackManager.RepeatType int repeatState) {
        this.m_vPlaybackManager.onSetRepeatState(repeatState);
    }

    public void setSeekbarPosition(int position) {
        this.m_vPlaybackManager.onSeekTo(position);
    }
}
