package com.example.softmusic_beta.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.example.softmusic_beta.glide.audiocover.AudioFileCover;
import com.example.softmusic_beta.glide.audiocover.AudioFileCoverLoader;

import java.io.InputStream;

public class SampleGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.prepend(
                AudioFileCover.class,
                InputStream.class,
                new AudioFileCoverLoader.Factory());

    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
