package com.example.mediaplayer.utils;

import android.annotation.SuppressLint;
import android.media.session.PlaybackState;
import android.os.SystemClock;

import com.example.mediaplayer.PlaybackManager;

import java.util.concurrent.atomic.AtomicBoolean;

public class PlaybackSubThread implements Runnable {

    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private AtomicBoolean isStopped = new AtomicBoolean(false);

    private final int m_vInterval;
    private final Thread m_vWorker;
    private final PlaybackManager m_vPlaybackManager;

    public PlaybackSubThread(int interval, PlaybackManager playbackManager) {
        this.m_vPlaybackManager = playbackManager;
        this.m_vWorker = new Thread(this);
        this.m_vInterval = interval;
    }

    @Override
    public void run() {
        this.isRunning.set(true);
        this.isStopped.set(false);

        while (isRunning()) {
            if (isStopped()) {
                break;

            }

            int state = this.m_vPlaybackManager.getPlaybackState();
            long actions = this.m_vPlaybackManager.getAvailableActions();
            int position = this.m_vPlaybackManager.getPlaybackPosition();

            if (this.m_vPlaybackManager.isPlayingOrPaused()) {
                @SuppressLint("Wrong constants")
                PlaybackState.Builder builder = new PlaybackState.Builder()
                        .setActions(actions)
                        .setState(state, position, 1.0F, SystemClock.elapsedRealtime());

                this.m_vPlaybackManager.getPlaybackCallback().onPlaybackStateChanged(builder.build());
                if (state != PlaybackState.STATE_PLAYING) {
                    this.onStop();
                }
            }

            try {
                Thread.sleep(this.m_vInterval);
            } catch (InterruptedException ignore) {
                this.interrupt();
            }
        }

        isStopped.set(false);
        isRunning.set(false);
    }

    private void interrupt() {
        this.isStopped.set(false);
        this.m_vWorker.interrupt();
    }

    public Thread getWorker() { return this.m_vWorker; }

    public boolean isRunning() { return this.isRunning.get(); }

    public boolean isStopped() { return this.isStopped.get(); }

    public void onStart() { this.m_vWorker.start(); }

    public void onStop() { this.interrupt(); }

}
