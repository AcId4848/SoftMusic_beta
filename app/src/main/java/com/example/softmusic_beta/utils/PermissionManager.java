package com.example.softmusic_beta.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PermissionManager {
    public static boolean isPermissionGranted (Activity activity, String permission) {
        return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity activity, String permission, int requestCode) {
        activity.requestPermissions(new String[] { permission }, requestCode);
    }
}
