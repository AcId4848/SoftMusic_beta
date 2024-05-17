package com.example.mediaplayer.interfaces;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public interface CustomBroadcastReceiver {
    void onReceive (Context context, Intent intent);

    default BroadcastReceiver build() {

        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                CustomBroadcastReceiver.this.onReceive(context, intent);
            }
        };
    }
}
