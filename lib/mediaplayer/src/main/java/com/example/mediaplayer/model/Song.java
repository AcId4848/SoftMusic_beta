package com.example.mediaplayer.model;

public class Song {
    private long Id;
    private String Title;
    private long Duration;
    private String Data;
    private String AlbumName;
    private String ArtistName;
    private String DisplayName;

    public Song(long id, String title, long duration, String data, String albumName, String artistName, String displayName) {
        this.Id = id;
        this.Title = title;
        this.Duration = duration;
        this.Data = data;
        this.AlbumName = albumName;
        this.ArtistName = artistName;
        this.DisplayName = displayName;
    }

    public static Song emptySong() {
        return new Song(-1, "", -1, "", "", "", "");
    }

    public long getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public long getDuration() {
        return Duration;
    }

    public String getData() {
        return Data;
    }

    public String getAlbumName() {
        return AlbumName;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public String getDisplayName() {
        return DisplayName;
    }
}
