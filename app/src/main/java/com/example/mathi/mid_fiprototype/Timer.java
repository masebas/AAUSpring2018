package com.example.mathi.mid_fiprototype;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Timer extends AppCompatActivity {
    private float recievedTime1;
    private float recievedTime2;
    private float timerTime;
    private int stage;
    private TextView timer;
    private Chronometer mChronometer;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            timer.setText(Float.toString(timerTime));
        }
    };

    public void init(){
        recievedTime1 = MainActivity.getNumPickValue1();
        recievedTime2 = MainActivity.getNumPickValue2();
        timer = findViewById(R.id.timer);
        stage = 0;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        init();
    }

    @Override
    protected void onStart(){
        super.onStart();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                if(stage == 0) {
                    for (int i = 0; i <= recievedTime1; i += 0.25) {
                        try {
                            wait(250);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        timerTime += 0.25;
                        handler.sendEmptyMessage(0);
                        if(timerTime == recievedTime1){
                            stage = 1;
                        }
                    }
                }
                if(stage == 1){
                    for (int i = 0; i <= recievedTime2; i += 0.25) {
                        try {
                            wait(250);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        timerTime += 0.25;
                        handler.sendEmptyMessage(0);
                        if(timerTime == recievedTime2){
                            stage = 0;
                        }
                    }
                }
            }
        };
        Thread t = new Thread(r);
        //t.start();
    }

    public void calcTime(){

    }
}
