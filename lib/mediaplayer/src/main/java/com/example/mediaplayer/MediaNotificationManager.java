package com.example.mediaplayer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.media.MediaMetadata;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;

import com.example.mediaplayer.services.MediaPlayerService;
import com.example.mediaplayer.statics.IntentFields;

public class MediaNotificationManager extends BroadcastReceiver {

    public static final String TAG = MediaNotificationManager.class.getSimpleName();

    public static final String NOTIFICATION_NAME = MediaPlayerService.class.getSimpleName();

    public static final int NOTIFICATION_ID = 101;

    public final NotificationManager m_vNotificationManager;
    private final Notification.Action m_vPlayAction;
    private final Notification.Action m_vPauseAction;
    private final Notification.Action m_vPlayNextAction;
    private final Notification.Action m_vPlayPrevAction;
    private final Notification.Action m_vFavouriteSongAction;

    private final MediaPlayerService m_vService;

    private boolean m_vStarted;
    private boolean isRegistered;

    public MediaNotificationManager(MediaPlayerService service) {
        this.m_vService = service;

        this.m_vStarted = false;
        this.isRegistered= false;

        String pkgName = this.m_vService.getPackageName();
        PendingIntent playActionIntent = PendingIntent.getBroadcast((Context) this.m_vService, 100, new Intent(IntentFields.ACTION_PLAY).setPackage(pkgName), PendingIntent.FLAG_IMMUTABLE);
        PendingIntent pauseActionIntent = PendingIntent.getBroadcast((Context) this.m_vService, 100, new Intent(IntentFields.ACTION_PAUSE).setPackage(pkgName), PendingIntent.FLAG_IMMUTABLE);
        PendingIntent playPrevActionIntent = PendingIntent.getBroadcast((Context) this.m_vService, 100, new Intent(IntentFields.ACTION_PREV).setPackage(pkgName), PendingIntent.FLAG_IMMUTABLE);
        PendingIntent playNextActionIntent = PendingIntent.getBroadcast((Context) this.m_vService, 100, new Intent(IntentFields.ACTION_NEXT).setPackage(pkgName), PendingIntent.FLAG_IMMUTABLE);
        PendingIntent MakeSongFavourite = PendingIntent.getBroadcast((Context) this.m_vService, 100, new Intent(IntentFields.ACTION_FAVOURITE).setPackage(pkgName), PendingIntent.FLAG_IMMUTABLE);


        this.m_vPlayAction = new Notification.Action.Builder(Icon.createWithResource((Context) this.m_vService, com.example.icons_pack.R.drawable.play_arrow_40px), "Play", playActionIntent).build();
        this.m_vPauseAction = new Notification.Action.Builder(Icon.createWithResource((Context) this.m_vService, com.example.icons_pack.R.drawable.audio_file_40px), "Pause", playActionIntent).build();
        this.m_vPlayNextAction = new Notification.Action.Builder(Icon.createWithResource((Context) this.m_vService, com.example.icons_pack.R.drawable.skip_next_40px), "Play Previous", playActionIntent).build();
        this.m_vPlayPrevAction = new Notification.Action.Builder(Icon.createWithResource((Context) this.m_vService, com.example.icons_pack.R.drawable.skip_previous_40px), "Play Next", playActionIntent).build();
        this.m_vFavouriteSongAction = new Notification.Action.Builder(Icon.createWithResource((Context) this.m_vService, com.example.icons_pack.R.drawable.favorite_40px), "Favourite", playActionIntent).build();

        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(IntentFields.ACTION_PLAY);
        intentFilter.addAction(IntentFields.ACTION_PAUSE);
        intentFilter.addAction(IntentFields.ACTION_PREV);
        intentFilter.addAction(IntentFields.ACTION_NEXT);
        intentFilter.addAction(IntentFields.ACTION_FAVOURITE);

        this.m_vService.registerReceiver(this, intentFilter, Context.RECEIVER_NOT_EXPORTED);
        this.isRegistered = true;

        this.m_vNotificationManager = this.m_vService.getSystemService(NotificationManager.class);
        this.m_vNotificationManager.cancelAll();

        NotificationChannel channel = new NotificationChannel(pkgName, NOTIFICATION_NAME, NotificationManager.IMPORTANCE_NONE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        this.m_vNotificationManager.createNotificationChannel(channel);

    }

    public void onStop() {
        if (this.m_vStarted) {
            this.m_vStarted = false;
            try {
                this.m_vNotificationManager.cancel(NOTIFICATION_ID);
                this.m_vService.unregisterReceiver(this);
            }
            catch (Exception ignore) { }
            this.m_vService.stopForeground(true);
        }
        else if (this.isRegistered) {
            try {
                this.m_vService.unregisterReceiver(this);
            } catch (Exception ignore) {

            }
        }
    }

    private PendingIntent getContentIntent() {
        Intent intent = new Intent("com.example.softmusic_beta.MainActivity");
        Intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        return PendingIntent.getActivity((Context) this.m_vService, 100, intent, PendingIntent.FLAG_IMMUTABLE);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case IntentFields.ACTION_PLAY:
                this.m_vService.onPlay();
                break;
            case IntentFields.ACTION_PAUSE:
                this.m_vService.onPause();
                break;
            case IntentFields.ACTION_PREV:
                this.m_vService.onPlayPrev();
                break;
            case IntentFields.ACTION_NEXT:
                this.m_vService.onPlayPrev();
                break;
            case IntentFields.ACTION_FAVOURITE:
                this.m_vService.onFavourite();
                break;
        }
    }

    public void onUpdateNotification(MediaMetadata mediaMetadata, PlaybackState playbackState, MediaSession.Token sessionToken) {
        
    }
}
