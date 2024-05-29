package com.example.softmusic_beta.ui;

import android.media.session.MediaController;

import com.example.mediaplayer.CorePlayer;
import com.example.mediaplayer.interfaces.PlayerCallback;
import com.example.mediaplayer.statics.ClassManager;
import com.example.softmusic_beta.MainActivity;

public class MediaPlayerManager {
    private static MediaPlayerManager instance;

    private CorePlayer m_vCorePlayer;

    private PlayerCallback m_vCallback;

    public MediaPlayerManager(MainActivity activity, MediaController.Callback callback) {
        ClassManager.init(MainActivity.class);

        this.m_vCorePlayer = new CorePlayer(activity, callback);
        this.m_vCallback = this.m_vCorePlayer.getCallback();

        setInstance(this);
    }

    private void setInstance(MediaPlayerManager instance) {
        if (MediaPlayerManager.instance == null) {
            MediaPlayerManager.instance = instance;
        }
    }

    public static MediaPlayerManager getInstance() {
        return instance;
    }

    public PlayerCallback getCallback() {
        return this.m_vCallback;
    }

    public void onStart() {
        this.m_vCorePlayer.onStart();
    }

    public void onDestroy() {
        this.m_vCorePlayer.onDestroy();
    }
}
