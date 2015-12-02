package com.example.bradperkins.project1;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private static final String PLAYER_STATE = MainActivity.PLAYER_STATE;

//    BoundService mService;
    boolean mBound;
    BoundService mBoundService = new BoundService();
    public MediaPlayer mPlayer;
    ArrayList<MusicData> musicData = new ArrayList<MusicData>();

    String song1 = "dontmatter";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adds Music Data
        String song1 = String.valueOf(R.raw.supersoaker);
        String song2 = (String.valueOf(R.raw.comebackstory));
        String song3 = (String.valueOf(R.raw.dontmatter));

        musicData.add(new MusicData("Kings of Leon", "Super Soaker", song1));
        musicData.add(new MusicData("Kings of Leon", "Comeback Story", song2));
        musicData.add(new MusicData("Kings of Leon", "Dont Matter", song3));


        findViewById(R.id.playBtn).setOnClickListener(this);
        findViewById(R.id.stopBtn).setOnClickListener(this);
        findViewById(R.id.pauseBtn).setOnClickListener(this);

    }

    public void onClick(View view){
        Intent intent = new Intent(this, BoundService.class);
        if(view.getId() == R.id.playBtn){
            startService(intent);
            bindService(intent, this, Context.BIND_AUTO_CREATE);
            mBound = true;
            mBoundService.play(this);
        }
        else if(view.getId() == R.id.stopBtn) {
            Log.i("MainActivity", "Stop tapped!!!!!");

            mBoundService.stop();
            stopService(intent);
        }
        else if (view.getId() == R.id.pauseBtn){
            pause();
        }
    }

    public void pause(){
        if (mBound = true) {
            mBoundService.pause();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, BoundService.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(this);
    }



    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        BoundService.BoundServiceBinder binder = (BoundService.BoundServiceBinder)service;
        mBoundService = binder.getService();
        mBound = true;
        Log.i("MainActivity", "onServiceConnected!!!!!");

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }




//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        String stateSaved = savedInstanceState.getString("saved_state");
//        mPlayer.start();
//
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//
//        MediaPlayer stateToSave = mPlayer;
//        outState.putString("saved_state", String.valueOf(stateToSave));
//
//    }
//
//

}
