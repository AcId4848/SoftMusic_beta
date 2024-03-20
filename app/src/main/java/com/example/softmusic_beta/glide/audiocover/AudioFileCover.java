package com.example.softmusic_beta.glide.audiocover;

import android.provider.MediaStore;

import androidx.annotation.Nullable;

import java.util.Objects;

public class AudioFileCover {
    private String Path;

    public AudioFileCover(String path) {
        this.Path = path;
    }

    public String getFilePath() {
        return this.Path;
    }

    @Override
    public int hashCode() {
        return this.Path.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof AudioFileCover))
            return false;

        return Objects.equals(((AudioFileCover)obj).Path, this.Path);
    }


}
