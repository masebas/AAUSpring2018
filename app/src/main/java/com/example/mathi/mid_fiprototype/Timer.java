package com.example.mathi.mid_fiprototype;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import static android.os.VibrationEffect.createOneShot;

public class Timer extends AppCompatActivity {

    //Declare Variables
    private long timeMillis1;
    private long timeMillis2;
    private TextView timer;
    private CountDownTimer countDownTimer;
    private Button mStartButton;
    private Button mStopButton;
    private boolean vibOn;
    private boolean soundOn;
    private Vibrator vib;
    private MediaPlayer mp;


    //Declare static variables
    private static int buffer;
    private static int vibAmp;
    //Setters for static variables
    public static void setVibAmp(int vibAmp) {
        Timer.vibAmp = vibAmp;
    }
    public static void setBuffer(int buffer) {
        Timer.buffer = buffer;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);                                                    //Set the layout file to R.layout.activity_timer

        timer = findViewById(R.id.timer);                                                           //Initialize the CountDownTimer text view
        Intent intent = getIntent();                                                                //Gets the intent passed from the MainActivity QuickStart fragment
        Bundle extras = intent.getExtras();                                                         //Gets the extras from the intent
        vibOn = extras.getBoolean("vibOn");                                                    //Gets the vibOn value from extras
        soundOn = extras.getBoolean("soundOn");                                                //Gets the soundOn value from extras
        timeMillis1 = extras.getLong("time1") *1000;                                           //Gets the time1 value from extras and multiplies it by 1000 to get it in millis
        timeMillis2 = extras.getLong("time2") *1000;                                           //Gets the time2 value from extras and multiplies it by 1000 to get it in millis


        mStartButton = findViewById(R.id.button_start);                                             //Initialize mStartButton
        mStopButton = findViewById(R.id.button_stop);                                               //Initialize mStopButton
        vib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);                                 //Initialize Vibrator service
        mStartButton.setEnabled(false);                                                             //Disables the restart button from the beginning
        calcTime(-1);                                                                         //Calls the calcTime method and starts it at stage -1


    }

    @Override
    protected void onPause() {                                                                      //Method called when activity is paused
        super.onPause();
        //Releases the MediaPlayer
        finish();                                                                                   //Finishes the activity and kills it
    }

    @Override
    protected void onStop() {                                                                       //Method called when activity is stopped
        super.onStop();
        //Releases the MediaPlayer
        finish();                                                                                   //Finishes the activity and kills it
    }
    public void playInterval(){                                                                     //Method to play the interval sound
        mp = MediaPlayer.create(this, R.raw.interval);                                        //Initializes a new MediaPlayer with the Finish sound
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.release();
            }
        });
        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                mp.release();
                return true;
            }
        });
        mp.start();
    }
    public void playFinish(){                                                                       //Method to play the Finish sound
        mp = MediaPlayer.create(this, R.raw.finish);                                        //Initializes a new MediaPlayer with the Finish sound
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.release();
            }
        });
        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                mp.release();
                return true;
            }
        });
        mp.start();                                                                                 //Starts the MediaPlayer
    }
    public void releasePlay(){
        if(mp != null){
            mp.release();
        }
    }

    @TargetApi(26)                                                                                  //Sets the target API of the method to 26
    public void vibrate(){                                                                          //Method to call the vibrate effect
        if (vib.hasVibrator()) {                                                                    //Condition check to make sure the device has a vibrator
            vib.vibrate(createOneShot(500, vibAmp));                                     //Creates a OneShot vibration of 500 millis with vibAmp amplitude
        }
    }

    @Override
    public void onBackPressed() {                                                                   //Method called when the back button is pressed
        super.onBackPressed();
        countDownTimer.cancel();                                                                    //Cancels the countDownTimer
        //Stops the MediaPlayer
        mp.release();                                                                               //Releases the MediaPlayer
    }

    public void calcTime(int stage){                                                                //Countdown Timer method

        if(stage == -1){
            //Stage -1
            countDownTimer = new CountDownTimer(buffer*1000, 1) {      //Initializes the CountDownTimer for the buffer, with an interval of 1 milli
                @Override
                public void onTick(long l) {                                                        //Called every time the countdown interval passes
                    timer.setText(Long.toString(l/100));                                         //Updates the TextView in 10th of a second
                }

                @Override
                public void onFinish() {                                                            //Called on CountDownTimer finish
                    if(soundOn){                                                                    //Conditional check to see if sound is on
                        releasePlay();                                                               //Releases the old MediaPlayer
                        playFinish();
                        //Calls the finish MediaPlayer
                    }
                    calcTime(0);                                                              //Calls stage 0
                }
            };
            countDownTimer.start();                                                                 //Starts the Buffer timer

        }
        if(stage == 0) {                                                                            //Stage 0
            if(soundOn)playFinish();
            countDownTimer = new CountDownTimer(timeMillis1, 1) {                   //Initializes the CountDownTimer for the Concentric
                @Override
                public void onTick(long l) {                                                        //Called on tick
                    timer.setText(Long.toString(l/100));                                         //Updates the TextView
                    if(soundOn) {                                                                   //Conditional check to check if sound is on
                        if(l <= 1000 && l >= 950) {                                                 //Checks the timer and plays a sound every 4 seconds
                            releasePlay();;                                                           //Releases the MediaPlayer
                            playInterval();}                                                        //Calls the interval sound MediaPlayer
                        if(l <= 2000 && l >= 1950) {
                            releasePlay();
                            playInterval();}
                        if(l <= 3000 && l >= 2950) {
                            releasePlay();
                            playInterval();}
                        if(l <= 4000 && l >= 3950) {
                            releasePlay();
                            playInterval();}
                    }
                }

                @Override
                public void onFinish() {                                                            //Called on CountDownTimer finish
                    if(vibOn){                                                                      //Conditional check to check if vibration is on
                        vibrate();                                                                  //Calls the vibrator
                    }
                    if(soundOn){                                                                    //Conditional check to see if sound is on
                        releasePlay();                                                               //Releases the old MediaPlayer
                        playFinish();
                        //Calls the finish MediaPlayer
                    }
                    calcTime(1);
                }
            };
            countDownTimer.start();                                                                 //Starts the CountDownTimer
        }
        if(stage == 1){                                                                             //Stage 1. Pretty identical to stage 0
            if(soundOn)playFinish();
            countDownTimer = new CountDownTimer(timeMillis2, 1) {
                @Override
                public void onTick(long l) {
                    timer.setText(Long.toString(l/100));
                    if(soundOn) {
                        if(l <= 1000 && l >= 950) {
                            releasePlay();;
                            playInterval();}
                        if(l <= 2000 && l >= 1950) {
                            releasePlay();
                            playInterval();}
                        if(l <= 3000 && l >= 2950) {
                            releasePlay();
                            playInterval();}
                        if(l <= 4000 && l >= 3950) {
                            releasePlay();
                            playInterval();}
                    }
                }

                @Override
                public void onFinish() {
                    if(vibOn){
                        vibrate();
                    }
                    if(soundOn){
                        releasePlay();
                        playFinish();
                    }
                    calcTime(0);
                }
            };
            countDownTimer.start();
        }
        mStartButton.setOnClickListener(new View.OnClickListener() {                                //Calls a new OnClickListener
            @Override
            public void onClick(View view){
                countDownTimer.start();                                                             //Restarts the CountDownTimer
                mStopButton.setEnabled(true);                                                       //Enables the stop button while timer is running
                mStartButton.setEnabled(false);                                                     //Disables the restart button while timer is running
            }
        });
        mStopButton.setOnClickListener(new View.OnClickListener() {                                 //Calls a new OnClickListener
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();                                                            //Cancels the CountDownTimer
                mStopButton.setEnabled(false);                                                      //Disables the stop button
                mStartButton.setEnabled(true);                                                      //Enables the restart button
            }
        });
    }
}
