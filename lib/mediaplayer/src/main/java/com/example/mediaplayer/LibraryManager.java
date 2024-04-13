package com.example.mediaplayer;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaDescription;
import android.media.MediaMetadata;
import android.provider.MediaStore;

import com.example.mediaplayer.model.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class LibraryManager {

    private final static String[] CURSOR_PROJECTION = new String[] {"_id", "artist", "album", "title", "duration", "_display_name", "_data", "_size"};

    public static List<Song> getSongs(Context context) {
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                CURSOR_PROJECTION,
                "is_music != 0",
                null,
                "_display_name ASC");

        List<Song> result = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = Integer.parseInt(getCursorStringByIndex(cursor, "_id"));
                String artistName = getCursorStringByIndex(cursor, "artist");
                String albumName = getCursorStringByIndex(cursor, "album");
                String title = getCursorStringByIndex(cursor, "title");
                String displayName = getCursorStringByIndex(cursor, "_display_name");
                String data = getCursorStringByIndex(cursor, "_data");
                long duration = getCursorLongByIndex(cursor, "duration");

                if (artistName == null || artistName.isEmpty())
                    artistName = "<no_name>";

                if (displayName.contains("AUD-") && !title.isEmpty())
                    displayName = title;

                result.add(new Song(
                        id,
                        title,
                        duration,
                        data,
                        albumName,
                        artistName,
                        displayName));
            }
        }
        if (cursor != null)
            cursor.close();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        return result;
    }

    public static TreeMap<Integer, Song> getTreemapOfSongs(List<Song> songs) {
        TreeMap<Integer, Song> results = new TreeMap<>();

        for (Song song : songs) {
            results.put((int)song.getId(), song);
        }

        return results;
    }

    public static MediaDescription getMediaDescription(Song song) {
        if (song == null) return null;

        MediaDescription.Builder builder = new MediaDescription.Builder();
        builder.setMediaId(String.valueOf(song.getId()));
        builder.setIconBitmap(getSongArt(song));
        builder.setTitle(song.getTitle());
        builder.setSubtitle(song.getArtistName());
        builder.setDescription(song.getTitle());

        return builder.build();
    }

    public static MediaMetadata getMediaMetadata(Song song) {
        if (song == null) return null;

        MediaMetadata.Builder builder = new MediaMetadata.Builder();
        builder.putString(MediaMetadata.METADATA_KEY_MEDIA_ID, String.valueOf(song.getId()));
        builder.putString(MediaMetadata.METADATA_KEY_ALBUM, song.getAlbumName());
        builder.putString(MediaMetadata.METADATA_KEY_ARTIST, song.getArtistName());
        builder.putString(MediaMetadata.METADATA_KEY_TITLE, song.getTitle());
        builder.putString(MediaMetadata.METADATA_KEY_DISPLAY_TITLE, song.getDisplayName());
        builder.putLong(MediaMetadata.METADATA_KEY_DURATION, song.getDuration());

        Bitmap songArt = getSongArt(song);

        if (songArt != null) {
            builder.putBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART, songArt);
        }

        return builder.build();
    }
    private static long getCursorLongByIndex(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return (index > -1) ? cursor.getLong(index) : -1L;
    }
    private static String getCursorStringByIndex(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return (index > -1) ? cursor.getString(index) : "";
    }

    private static Bitmap getSongArt(Song song) {
        return null;
    }

}
