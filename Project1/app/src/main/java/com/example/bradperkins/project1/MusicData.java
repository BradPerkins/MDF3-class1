package com.example.bradperkins.project1;

/**
 * Created by bradperkins on 11/30/15.
 */
public class MusicData{

    public String title;
    public String artist;
    public int songUri;

    MusicData(String _artist, String _title, int _songUri ){
        artist = _artist;
        title = _title;
        songUri = _songUri;
    }

    public String getTitle() {
        return title;
    }


    public String getArtist() {
        return artist;
    }

    public int getSongUri() {
        return songUri;
    }


}
