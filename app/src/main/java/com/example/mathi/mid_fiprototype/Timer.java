package com.example.mathi.mid_fiprototype;

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
        timerIsRunning = true;
        toggle = findViewById(R.id.toggle);
    }
    @Override
    protected void onStart(){
        super.onStart();
        timerIsRunning = true;
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    timerIsRunning = true;
                } else {
                    timerIsRunning = false;
                }
            }
        });
        if(timerIsRunning){
            runTime();
        }
    }
    public void init(){
        timer = findViewById(R.id.timer);
        recievedTime1 = MainActivity.getNumPickValue1();
        recievedTime2 = MainActivity.getNumPickValue2();
    }
    public void runTime() {
        switch(timerID){
            case 0:
                while(timerTime <= recievedTime1) {
                    calcTime();
                    if (timerTime == recievedTime1) {
                        timerTime = 0;
                        timerID = 1;
                        break;
                    }
                }
                break;
            case 1:
                while(timerTime <= recievedTime2){
                    calcTime();
                    if(timerTime == recievedTime2){
                        timerTime = 0;
                        timerID = 0;
                        break;
                    }
                }
                break;
        }
    }
    public void calcTime(){
        float timer1 = java.lang.System.currentTimeMillis();
        while(elapsedTime < 0.25){
            test = Float.toString(timerTime);
            timer.setText(test);
            float timer2 = java.lang.System.currentTimeMillis();
            elapsedTime = timer1 - timer2;
            if(elapsedTime == 0.25){
                timerTime += 0.25;
            }
            if(elapsedTime > 0.25){
                elapsedTime = 0;
            }
        }
        timer.setText(Float.toString(timerTime));
    }
}
