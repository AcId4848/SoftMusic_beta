package com.example.mediaplayer.services;

import android.content.BroadcastReceiver;

import com.example.mediaplayer.interfaces.CustomBroadcastReceiver;
import com.example.mediaplayer.statics.IntentFields;
import android.content.IntentFilter;
import java.util.List;

public class MediaPlayerBroadcastHelper {
    private final MediaPlayerService m_vService;

    public MediaPlayerBroadcastHelper(MediaPlayerService service) {
        this.m_vService = service;
    }

    private final BroadcastReceiver onPlay = ((CustomBroadcastReceiver) (context, intent) -> {
        List<Integer> queue = intent.getIntegerArrayListExtra(IntentFields.EXTRA_TRACKS_QUEUE);
        int index = intent.getIntExtra(IntentFields.EXTRA_TRACK_INDEX, -1);

        if (index < 0 || queue.isEmpty()) return;

        MediaPlayerBroadcastHelper.this.m_vService.onPlay(queue, index);
    }).build();

    private final BroadcastReceiver onPlayIndex = ((CustomBroadcastReceiver) (context, intent) -> {
        int index = intent.getIntExtra(IntentFields.EXTRA_TRACK_INDEX, - 1);

        if (index < 0) return;

        MediaPlayerBroadcastHelper.this.m_vService.onPlayIndex(index);
    }).build();

    private final BroadcastReceiver onPlayNext = ((CustomBroadcastReceiver) (context, intent) -> {
        MediaPlayerBroadcastHelper.this.m_vService.onPlayNext();
    }).build();

    private final BroadcastReceiver onPlayPause = ((CustomBroadcastReceiver) (context, intent) -> {
        MediaPlayerBroadcastHelper.this.m_vService.onPlayPause();
    }).build();

    private final BroadcastReceiver onPlayPrev = ((CustomBroadcastReceiver) (context, intent) -> {
        MediaPlayerBroadcastHelper.this.m_vService.onPlayPrev();
    }).build();

    private final BroadcastReceiver onUpdateQueue = ((CustomBroadcastReceiver) (context, intent) -> {
        List<Integer> queue = intent.getIntegerArrayListExtra(IntentFields.EXTRA_TRACKS_QUEUE);
        int index = intent.getIntExtra(IntentFields.EXTRA_TRACK_INDEX, -1);

        if (index < 0 || queue.size() == 0) return;

        MediaPlayerBroadcastHelper.this.m_vService.onUpdateQueue(queue, index);
    }).build();

    private final BroadcastReceiver setSeekbarPosition = ((CustomBroadcastReceiver) (context, intent) -> {
        int position = intent.getIntExtra(IntentFields.EXTRA_SEEK_BAR_POSITION, -1);

        if (position < 0) return;

        MediaPlayerBroadcastHelper.this.m_vService.setSeekbarPosition(position);
    }).build();

    private final BroadcastReceiver setRepeatState = ((CustomBroadcastReceiver) (context, intent) -> {
        int state = intent.getIntExtra(IntentFields.EXTRA_REPEAT_STATE, -1);
    }).build();

    public void registerReceivers() {
        this.m_vService.registerReceiver(this.onPlay, new IntentFilter(IntentFields.INTENT_PLAY));
        this.m_vService.registerReceiver(this.onPlayIndex, new IntentFilter(IntentFields.INTENT_PLAY_INDEX));
        this.m_vService.registerReceiver(this.onPlayNext, new IntentFilter(IntentFields.INTENT_PLAY_NEXT));
        this.m_vService.registerReceiver(this.onPlayPrev, new IntentFilter(IntentFields.INTENT_PLAY_PREV));
        this.m_vService.registerReceiver(this.onPlayPause, new IntentFilter(IntentFields.INTENT_PLAY_PAUSE));
        this.m_vService.registerReceiver(this.onUpdateQueue, new IntentFilter(IntentFields.INTENT_UPDATE_QUEUE));
        this.m_vService.registerReceiver(this.setSeekbarPosition, new IntentFilter(IntentFields.INTENT_SET_SEEKBAR));
        this.m_vService.registerReceiver(this.setRepeatState, new IntentFilter(IntentFields.INTENT_CHANGE_REPEAT));
    }

    public void unregisterReceivers() {
        this.m_vService.unregisterReceiver(this.onPlay);
        this.m_vService.unregisterReceiver(this.onPlayIndex);
        this.m_vService.unregisterReceiver(this.onPlayNext);
        this.m_vService.unregisterReceiver(this.onPlayPrev);
        this.m_vService.unregisterReceiver(this.onPlayPause);
        this.m_vService.unregisterReceiver(this.onUpdateQueue);
        this.m_vService.unregisterReceiver(this.setSeekbarPosition;
        this.m_vService.unregisterReceiver(this.setRepeatState);
    }
}
