package com.example.mediaplayer;

import android.app.Activity;
import android.content.Intent;
import android.media.session.MediaController;

import com.example.mediaplayer.interfaces.PlayerCallback;
import com.example.mediaplayer.statics.IntentFields;

import java.util.ArrayList;
import java.util.List;

public class CorePlayer {
    private final Activity m_vActivity;
    private PlayerCallback m_vCallback;
    private MediaPlayerController m_vMediaPlayerController;

    public CorePlayer(Activity activity, MediaController.Callback callback) {
        this.m_vActivity = activity;
        this.m_vCallback = getCallback();
        this.m_vMediaPlayerController = new MediaPlayerController(activity, callback);
    }

    public PlayerCallback getCallback() {
        this.m_vCallback = (this.m_vCallback == null) ? new PlayerCallback() {
            @Override
            public void onClickPlay(int queueIndex, List<Integer> queue) {
                ArrayList<Integer> new_queue = new ArrayList<>();
                for (int i : queue) { new_queue.add(i); }

                Intent intent = new Intent(IntentFields.INTENT_PLAY);
                intent.putIntegerArrayListExtra(IntentFields.EXTRA_TRACKS_QUEUE, new_queue);
                intent.putExtra(IntentFields.EXTRA_TRACK_INDEX, queueIndex);

                CorePlayer.this.m_vActivity.sendBroadcast(intent);
            }

            @Override
            public void onClickPlayIndex(int index) {

            }

            @Override
            public void onClickPlayNext() {

            }

            @Override
            public void onClickPlayPrev() {

            }

            @Override
            public void onClickPause() {
                Intent intent = new Intent(IntentFields.INTENT_PLAY_PAUSE);
                CorePlayer.this.m_vActivity.sendBroadcast(intent);
            }

            @Override
            public void onSetSeekbar(int position) {

            }

            @Override
            public void onUpdateQueue(List<Integer> queue, int queueIndex) {

            }

            @Override
            public void onDestroy() {

            }
        } : this.m_vCallback;

        return this.m_vCallback;
    }

    public void onDestroy() {
        this.m_vMediaPlayerController.onDestroy();
    }
    public void onStart() {
        this.m_vMediaPlayerController.onStart();
    }
}
