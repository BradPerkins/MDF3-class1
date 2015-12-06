package com.example.bradperkins.project1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by bradperkins on 11/30/15.
 */
public class BoundService extends Service {


    public MediaPlayer mPlayer;
    MusicData mMusicData;
    int song1 = R.raw.supersoaker;
    public ArrayList<MusicData> musicList;
    public int songIndex = 0;


    public class BoundServiceBinder extends Binder {
        public BoundService getService() {
            return BoundService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new BoundServiceBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        //play(this, String.valueOf(R.raw.dontmatter));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        Log.i("BoundService", "Service Destroyed!!!!!");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("BoundService", "onUnbind!!!!!");
        return super.onUnbind(intent);
    }

    //Play Player
    public void play(){

        int demo = R.raw.comebackstory;

        stop();


        int current = musicList.get(songIndex).getSongUri();

        mPlayer = MediaPlayer.create(this, current);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {

                stop();
            }
        });
        int length = 0;
        length = mPlayer.getCurrentPosition();
        mPlayer.seekTo(length);
        mPlayer.start();
    }

    //Stop Player
    public void stop(){
        if (mPlayer != null){
            mPlayer.release();
            mPlayer = null;
            Toast.makeText(this, "Song Stopped", Toast.LENGTH_SHORT).show();
        }
    }

    //Pause Player
    public void pause(){
        int length = 0;
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        } else {
            //resume song
            length = mPlayer.getCurrentPosition();
            mPlayer.seekTo(length);
            mPlayer.start();
        }
    }



}
