package com.example.bradperkins.project2;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.SeekBar;

import java.io.IOException;

/**
 * Created by bradperkins on 12/3/15.
 */
public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    public static final int EXPANDED_NOTIFICATION = 0x01002;

    public AddMusicData addMusicData = new AddMusicData();
    public MediaPlayer mPlayer;
    public int currentPos;

    Handler seekHandler = new Handler();
    int curr;//current position for seekBar

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.i("SERVICE", "The service is prepared");
        mPlayer.start();
        MainActivity.seekBar.setMax(mPlayer.getDuration());
        seekHandler.postDelayed(seekUpdate, 1000);
        seekBar();

    }

    //SeekBar Runnable
    Runnable seekUpdate = new Runnable() {
        @Override
        public void run() {
            if (mPlayer != null && mPlayer.isPlaying()){
                curr = mPlayer.getCurrentPosition();
                MainActivity.seekBar.setProgress(curr);
            }else {
               MainActivity.seekBar.setProgress(curr);
            }
            seekHandler.postDelayed(this, 1000);
        }
    };

    public void updateBar(){
        seekHandler.postDelayed(seekUpdate, 1000);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        seekBar();

    }

    @Override
    public void onCompletion(MediaPlayer mPlayer) {
        if(mPlayer.getCurrentPosition()>0){
            mPlayer.reset();
            next();
        }
    }

    public class MusicServiceBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MusicServiceBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        if (mPlayer !=null){
            mPlayer.release();
        }
    }

    //Playing track method
    public void play(int songIndex) {

        if(mPlayer == null) {
            currentPos = MainActivity.songIndex;
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(this);

            //Song URI
            int currentUri = addMusicData.getIndexPos(songIndex);
            String uri = "android.resource://" + getPackageName() + "/" + currentUri;

            //Song Title
            String currentTitle = addMusicData.getSongTitle(songIndex);
            String songTitle = currentTitle;

            //Artist Name
            String currentArtist = addMusicData.getArtist(songIndex);
            String artist = currentArtist;

            int pic = addMusicData.getAlbumCover(songIndex);
            Bitmap albumCover = BitmapFactory.decodeResource(getResources(), pic);

            MainActivity.artistName = artist;
            MainActivity.songName = songTitle;
            MainActivity.albumCover = albumCover;

            MainActivity.titleLabel.setText(MainActivity.songName);
            MainActivity.artistLabel.setText(MainActivity.artistName);
            MainActivity.albumImage.setImageBitmap(MainActivity.albumCover);

            try {
                mPlayer.setDataSource(this, Uri.parse((uri)));

            } catch (IOException e) {
                e.printStackTrace();
                mPlayer.release();
                mPlayer = null;
            }
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    mp.stop();
                    mp.release();
                    next();
                }
            });

            notify(songTitle, albumCover, artist);

            mPlayer.prepareAsync();
            updateBar();
        }
        else if(MainActivity.looping == true){

        }
    }

    //Stop Player
    public void stop(){
        if (mPlayer != null){
            mPlayer.release();
            mPlayer = null;
        }
    }

    //Pause Player
    public void pause(){
        int length;
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        } else {
            //resume song
            length = mPlayer.getCurrentPosition();
            mPlayer.seekTo(length);
            mPlayer.start();
        }
    }

    //Next Track Method
    public void next() {
        stop();
        if(MainActivity.looping == true){
            play(currentPos);
        }
        else{
            MainActivity.songIndex++;
            if (MainActivity.songIndex == 4){
                MainActivity.songIndex = 0;
            }
            play(MainActivity.songIndex);
        }
    }

    //Previous Track Method
    public void prev() {
        stop();
        if (MainActivity.looping == true){
            play(currentPos);
        }
        else {
            MainActivity.songIndex--;
            if (MainActivity.songIndex == -1) {
                MainActivity.songIndex = 3;
            }
            play(MainActivity.songIndex);
        }
    }


    //SeekBar Method for user interaction change pro
    public void seekBar(){

        MainActivity.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("SeekBar", "Seeker Change  ProgressChanged");
                if (mPlayer != null && fromUser) {
                    mPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i("SeekBar", "Seeker Change  onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i("SeekBar", "Seeker Change  onStopTrackingTouch");
            }
        });

    }

    //SEEK
    public void seek(int posn) {
        mPlayer.seekTo(posn);
    }

    //Notification
    public void notify(String title, Bitmap albumCover, String artist){

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, EXPANDED_NOTIFICATION, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.demo_image);
        builder.setLargeIcon(albumCover);
        builder.setContentTitle(artist);
        builder.setContentText(title);
        Notification.BigTextStyle bigStyle = new Notification.BigTextStyle();
        bigStyle.bigText(title);
        bigStyle.setBigContentTitle(title);
        bigStyle.setSummaryText(title);

        //Add the Large Image in the notification
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(albumCover));
        builder.setOngoing(true);
        builder.setContentIntent(pIntent);
        startForeground(EXPANDED_NOTIFICATION, builder.build());



    }


}
