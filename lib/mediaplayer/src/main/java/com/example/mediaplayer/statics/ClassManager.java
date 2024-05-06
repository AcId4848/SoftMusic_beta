package com.example.mediaplayer.statics;

import androidx.annotation.NonNull;

public class ClassManager {
    private static ClassManager _instance;

    private Class<?> m_vActivity;

    private ClassManager(Class<?> activity) {
        this.m_vActivity = activity;
    }

    public static Class<?> getActivity() {
        if (_instance == null)
            throw new RuntimeException("instance was not created");

        return _instance.m_vActivity;
    }

    public static void init(@NonNull Class<?> activity) {
        if (_instance == null || _instance.m_vActivity == null) {
            _instance = new ClassManager(activity);
        }
    }
}
