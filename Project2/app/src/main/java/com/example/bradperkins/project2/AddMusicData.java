package com.example.bradperkins.project2;

import java.util.ArrayList;

/**
 * Created by bradperkins on 12/3/15.
 */
public class AddMusicData {

    public ArrayList<MusicData> musicList = new ArrayList<>();

    public AddMusicData(){
        musicList.add(new MusicData("Kings of Leon", "Super Soaker", R.raw.supersoaker, R.drawable.album1));
        musicList.add(new MusicData("Kings of Leon", "Dont Matter", R.raw.dontmatter, R.drawable.album2));
        musicList.add(new MusicData("Kings of Leon", "Comeback Story", R.raw.comebackstory, R.drawable.album3));
        musicList.add(new MusicData("Kings of Leon", "Radioactive", R.raw.radioactive, R.drawable.album4));

    }

    //Current Position of song URI
    public int getIndexPos(int songIndex){
        return musicList.get(songIndex).getSongUri();
    }

    public String getSongTitle(int songIndex){
        return musicList.get(songIndex).getTitle();
    }

    public String getArtist(int songIndex){
        return musicList.get(songIndex).getArtist();
    }

    public int getAlbumCover(int songIndex) {
        return musicList.get(songIndex).getCover();
    }
}
