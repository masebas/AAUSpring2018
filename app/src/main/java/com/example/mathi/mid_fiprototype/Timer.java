package com.example.mathi.mid_fiprototype;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Timer extends AppCompatActivity {
    private boolean timerIsRunning;
    private int currentPhase;
    private float timerTime;
    private float recievedTime1;
    private float recievedTime2;
    private float displayTime;
    private float elapsedTime;
    private TextView timer;
    private String test;
    private int timerID = 0;
    private ToggleButton toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        init();
    }
    @Override
    protected void onStart(){
        super.onStart();
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                timer.setText(Float.toString(timerTime));
            }
        };
        Runnable r = new Runnable() {
            @Override
            public void run() {
                switch(timerID){
                    case 0:
                        while(timerTime <= recievedTime1) {
                            float timer1 = java.lang.System.currentTimeMillis();
                            while(elapsedTime < 0.25){
                                float timer2 = java.lang.System.currentTimeMillis();
                                elapsedTime = timer1 - timer2;
                                if(elapsedTime == 0.25){
                                    timerTime += 0.25;
                                }
                                if(elapsedTime > 0.25){
                                    elapsedTime = 0;
                                }
                            }
                            if (timerTime == recievedTime1) {
                                timerTime = 0;
                                timerID = 1;
                                handler.sendEmptyMessage(0);
                                break;
                            }
                        }
                        break;
                    case 1:
                        while(timerTime <= recievedTime2){
                            float timer1 = java.lang.System.currentTimeMillis();
                            while(elapsedTime < 0.25){
                                float timer2 = java.lang.System.currentTimeMillis();
                                elapsedTime = timer1 - timer2;
                                if(elapsedTime == 0.25){
                                    timerTime += 0.25;
                                }
                                if(elapsedTime > 0.25){
                                    elapsedTime = 0;
                                }
                            }
                            if(timerTime == recievedTime2){
                                timerTime = 0;
                                timerID = 0;
                                handler.sendEmptyMessage(0);
                                break;
                            }
                        }
                        break;
                }
            }
        };
        final Thread t = new Thread(r);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    t.start();
                } else {
                    t.stop();
                }
            }
        });
        t.start();
    }
    public void init(){
        timer = findViewById(R.id.timer);
        toggle = findViewById(R.id.toggle);
        recievedTime1 = MainActivity.getNumPickValue1();
        recievedTime2 = MainActivity.getNumPickValue2();
    }

}
