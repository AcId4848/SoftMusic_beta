package com.example.mediaplayer.statics;

import android.content.Intent;

public class IntentFields {

    public static final String ACTION_NEXT = "com.example.softmusic_beta.NEXT";
    public static final String ACTION_PREV = "com.example.softmusic_beta.PREV";
    public static final String ACTION_PAUSE = "com.example.softmusic_beta.PAUSE";
    public static final String ACTION_PLAY = "com.example.softmusic_beta.PLAY";
    public static final String ACTION_FAVOURITE = "com.example.softmusic_beta.FAVOURITE";
    public static final String CHANNEL_ID = "com.example.softmusic_beta";

    public static final String EXTRA_REPEAT_STATE = "REPEAT_STATE";
    public static final String EXTRA_TRACKS_QUEUE = "TRACKS_QUEUE";
    public static final String EXTRA_TRACKS_ID = "TRACKS_ID";
    public static final String EXTRA_TRACK_INDEX = "TRACK_INDEX";
    public static final String EXTRA_SEEK_BAR_POSITION = "SEEK_BAR_POSITION";


    public static final String INTENT_ADD_TRACK_NEXT = "com.example.softmusic_beta.service.MediaPlayerService.ADD_TRACK_NEXT";
    public static final String INTENT_CHANGE_REPEAT = "com.example.softmusic_beta.service.MediaPlayerService.CHANGE_REPEAT";
    public static final String INTENT_PLAY = "com.example.softmusic_beta.service.MediaPlayerService.PLAY";
    public static final String INTENT_PLAY_INDEX = "com.example.softmusic_beta.service.MediaPlayerService.PLAY_INDEX";
    public static final String INTENT_PLAY_PAUSE = "com.example.softmusic_beta.service.MediaPlayerService.PLAY_PAUSE";
    public static final String INTENT_PLAY_PREV = "com.example.softmusic_beta.service.MediaPlayerService.PLAY_PREV";
    public static final String INTENT_PLAY_NEXT = "com.example.softmusic_beta.service.MediaPlayerService.PLAY_NEXT";
    public static final String INTENT_SET_SEEKBAR = "com.example.softmusic_beta.service.MediaPlayerService.SET_SEEKBAR";
    public static final String INTENT_UPDATE_QUEUE = "com.example.softmusic_beta.service.MediaPlayerService.UPDATE_QUEUE";


}
