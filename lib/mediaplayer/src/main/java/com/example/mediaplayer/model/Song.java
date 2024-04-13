package com.example.mediaplayer.model;

public class Song {
    private long m_vId;
    private String m_vTitle;
    private long m_vDuration;
    private String m_vData;
    private String m_vAlbumName;
    private String m_vArtistName;
    private String m_vDisplayName;

    public Song(long id, String title, long duration, String data, String albumName, String artistName, String displayName) {
        this.m_vId = id;
        this.m_vTitle = title;
        this.m_vDuration = duration;
        this.m_vData = data;
        this.m_vAlbumName = albumName;
        this.m_vArtistName = artistName;
        this.m_vDisplayName = displayName;
    }

    public static Song emptySong() {
        return new Song(-1, "", -1, "", "", "", "");
    }

    public long getId() {
        return m_vId;
    }

    public String getTitle() {
        return m_vTitle;
    }

    public long getDuration() {
        return m_vDuration;
    }

    public String getData() {
        return m_vData;
    }

    public String getAlbumName() {
        return m_vAlbumName;
    }

    public String getArtistName() {
        return m_vArtistName;
    }

    public String getDisplayName() {
        return m_vDisplayName;
    }
}
