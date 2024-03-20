package com.example.softmusic_beta.glide.audiocover;

import android.media.MediaMetadataRetriever;

import androidx.annotation.NonNull;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AudioFileCoverFetcher implements DataFetcher<InputStream> {

    private final AudioFileCover Model;

    private InputStream Stream;

    public AudioFileCoverFetcher(AudioFileCover model) {
        this.Model = model;
        this.Stream = null;
    }

    @Override
    public void loadData(@NonNull Priority priority, @NonNull DataCallback<? super InputStream> callback) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(this.Model.getFilePath());
            byte[] data = retriever.getEmbeddedPicture();

            if (data != null && data.length > 0)
                this.Stream = new ByteArrayInputStream(data);

            callback.onDataReady(this.Stream);
        }
        catch (Exception ex) {
            callback.onLoadFailed(ex);
        }
        finally {
            try {
                retriever.release();
            }
            catch (IOException ignore) {}
        }
    }

    @Override
    public void cleanup() {
        if (this.Stream != null) {
            try {
                this.Stream.close();
            }
            catch (Exception ignore) {

            }
        }
    }

    @Override
    public void cancel() {

    }

    @NonNull
    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.LOCAL;
    }
}
