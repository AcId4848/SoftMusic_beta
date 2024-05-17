package com.example.softmusic_beta;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.softmusic_beta.ui.UIThread;
import com.example.softmusic_beta.utils.PermissionManager;

import android.Manifest;
import android.view.View;
import android.widget.SearchView;

public class MainActivity extends AppCompatActivity {

    private UIThread m_vThread;
    private SearchView m_vSearchView;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      PermissionManager.requestPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE, 100);
      PermissionManager.requestPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, 100);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            if (!Environment.isExternalStorageManager()) {
//                Intent intent = new Intent(ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                Uri uri = Uri.fromParts("package", getPackageName(), null);
//                intent.setData(uri);
//                startActivity(intent);
//            }
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    100);
        }
        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, getPackageName());
        startActivity(intent);

        this.m_vThread = new UIThread(this);

    }

    public void startSettings_Library(View view) {
        Intent intent = new Intent(this, SettingsActivity_Library.class);
        startActivity(intent);
    }

    public void startSettings_AllMusic(View view) {
        Intent intent = new Intent(this, SettingsActivity_AllMusic.class);
        startActivity(intent);
    }
}