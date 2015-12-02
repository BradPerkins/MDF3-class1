package com.example.bradperkins.project1;

/**
 * Created by bradperkins on 11/30/15.
 */
public class MusicData{

    public String title;
    public String artist;
    public String songUri;

    MusicData(String _artist, String _title, String _songUri ){
        artist = _artist;
        title = _title;
        songUri = _songUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSongUri() {
        return songUri;
    }

    public void setSongUri(String songUri) {
        this.songUri = songUri;
    }

}
