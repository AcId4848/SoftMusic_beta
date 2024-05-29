package com.example.mediaplayer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ServiceInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.media.MediaDescription;
import android.media.MediaMetadata;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Build;
import android.util.Log;

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



        this.m_vPlayAction = new Notification.Action.Builder(Icon.createWithResource((Context) this.m_vService, com.example.icons_pack.R.drawable.play_arrow_40px), "Play", playActionIntent).build();
        this.m_vPauseAction = new Notification.Action.Builder(Icon.createWithResource((Context) this.m_vService, com.example.icons_pack.R.drawable.pause_40px), "Pause", pauseActionIntent).build();
        this.m_vPlayNextAction = new Notification.Action.Builder(Icon.createWithResource((Context) this.m_vService, com.example.icons_pack.R.drawable.skip_next_40px), "Play Next", playNextActionIntent).build();
        this.m_vPlayPrevAction = new Notification.Action.Builder(Icon.createWithResource((Context) this.m_vService, com.example.icons_pack.R.drawable.skip_previous_40px), "Play Previous", playPrevActionIntent).build();
        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(IntentFields.ACTION_PLAY);
        intentFilter.addAction(IntentFields.ACTION_PAUSE);
        intentFilter.addAction(IntentFields.ACTION_PREV);
        intentFilter.addAction(IntentFields.ACTION_NEXT);

        this.m_vService.registerReceiver(this, intentFilter, Context.RECEIVER_EXPORTED);
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
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

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
                this.m_vService.onPlayNext();
                break;
        }
    }

    public void onUpdateNotification(MediaMetadata mediaMetadata, PlaybackState playbackState, MediaSession.Token sessionToken) {
        if (playbackState == null || playbackState.getState() == PlaybackState.STATE_STOPPED || playbackState.getState() == PlaybackState.STATE_NONE) {
            this.m_vService.stopForeground(true);
            try {
                this.m_vService.unregisterReceiver(this);
            }
            catch (Exception ignore) {}
            this.m_vService.stopSelf();
            return;
        }

        boolean isPlaying = playbackState.getState() == PlaybackState.STATE_PLAYING;

        int[] actions = (this.m_vService.getPlaybackManager().canPlayPrev()) ? new int[] { 0, 1 } : new int[1];

        Notification.MediaStyle mediaStyle = new Notification.MediaStyle()
                .setMediaSession(sessionToken)
                .setShowActionsInCompactView(actions);

        MediaDescription mediaDescription = mediaMetadata.getDescription();

        Notification.Builder builder = new Notification.Builder((Context) this.m_vService, IntentFields.CHANNEL_ID)
                .setCategory("service")
                .setStyle((Notification.Style)mediaStyle)
                .setContentIntent(getContentIntent())
                .setSmallIcon(com.example.icons_pack.R.drawable.softmusicbeta)
                .setContentTitle(mediaDescription.getTitle())
                .setContentText(mediaDescription.getSubtitle())
                .setLargeIcon(mediaDescription.getIconBitmap())
                .setOngoing(isPlaying);

        Notification.Action action = isPlaying ? this.m_vPauseAction : this.m_vPlayAction;

        if (this.m_vService.getPlaybackManager().canPlayPrev()) {
            builder.addAction(this.m_vPlayPrevAction);
        }

        builder.addAction(action);

        if (this.m_vService.getPlaybackManager().canPlayNext()) {
            builder.addAction(this.m_vPlayNextAction);
        }

        Notification notification = builder.build();
        if (isPlaying && !this.m_vStarted) {
            Intent intent = new Intent((Context) this.m_vService, MediaPlayerService.class);
            this.m_vService.startForegroundService(intent);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                this.m_vService.startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK);
            }
            else {
                this.m_vService.startForeground(NOTIFICATION_ID, notification);
            }

            this.m_vStarted = true;
        }
        else {
            if (!isPlaying && this.m_vStarted) {
                this.m_vService.stopForeground(false);
                this.m_vStarted = false;
            }

            this.m_vNotificationManager.notify(NOTIFICATION_ID, notification);
        }
    }
}
