package com.example.softmusic_beta.ui.fragments;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.softmusic_beta.R;

public class FragmentSettings extends Fragment {
    public FragmentSettings(String value) {
        Log.d("FragmentSettings", "Value Parsed In :" + value);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflat, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflat.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
