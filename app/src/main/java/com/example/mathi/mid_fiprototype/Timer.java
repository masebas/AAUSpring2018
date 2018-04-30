package com.example.mathi.mid_fiprototype;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.os.VibrationEffect.createOneShot;

public class Timer extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timer = findViewById(R.id.timer);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        vibOn = extras.getBoolean("vibOn");
        soundOn = extras.getBoolean("soundOn");
        timeMillis1 = extras.getLong("time1") *1000;
        timeMillis2 = extras.getLong("time2") *1000;
        timer = findViewById(R.id.timer);
        mStartButton = findViewById(R.id.button_start);
        mStopButton = findViewById(R.id.button_stop);

        vib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        mStartButton.setEnabled(false);
        calcTime(0);


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
    public void playInterval(){
        mp = MediaPlayer.create(this, R.raw.interval);
        mp.start();

    }
    public void playFinish(){
        mp = MediaPlayer.create(this, R.raw.finish);
        mp.start();
    }

    @TargetApi(26)
    public void vibrate(){
        if (vib.hasVibrator()) {
            vib.vibrate(createOneShot(500, 255));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
        mp.stop();
        mp.release();
    }

    public void calcTime(int stage){
        mp = MediaPlayer.create(this,R.raw.interval);


        if(stage == 0) {
            countDownTimer = new CountDownTimer(timeMillis1, 1) {
                @Override
                public void onTick(long l) {
                    timer.setText(Long.toString(l/100));
                    if(soundOn) {
                        if(l <= 1000 && l >= 950) {
                            mp.release();
                            playInterval();}
                        if(l <= 2000 && l >= 1950) {
                            mp.release();
                            playInterval();}
                        if(l <= 3000 && l >= 2950) {
                            mp.release();
                            playInterval();}
                        if(l <= 4000 && l >= 3950) {
                            mp.release();
                            playInterval();}
                    }
                }

                @Override
                public void onFinish() {
                    if(vibOn){
                        vibrate();
                    }
                    if(soundOn){
                        mp.release();
                        playFinish();
                    }
                    calcTime(1);
                }
            };
            countDownTimer.start();
        }
        if(stage == 1){
            countDownTimer = new CountDownTimer(timeMillis2, 1) {
                @Override
                public void onTick(long l) {
                    timer.setText(Long.toString(l/100));
                    if(soundOn) {
                        if(l <= 1000 && l >= 950) {
                            mp.release();
                            playInterval();}
                        if(l <= 2000 && l >= 1950) {
                            mp.release();
                            playInterval();}
                        if(l <= 3000 && l >= 2950) {
                            mp.release();
                            playInterval();}
                        if(l <= 4000 && l >= 3950) {
                            mp.release();
                            playInterval();}
                    }
                }

                @Override
                public void onFinish() {
                    if(vibOn){
                        vibrate();
                    }
                    if(soundOn){
                        mp.release();
                        playFinish();
                    }
                    calcTime(0);
                }
            };
            countDownTimer.start();
        }
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                countDownTimer.start();
                mStopButton.setEnabled(true);
                mStartButton.setEnabled(false);
            }
        });
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                mStopButton.setEnabled(false);
                mStartButton.setEnabled(true);
            }
        });
    }
}
