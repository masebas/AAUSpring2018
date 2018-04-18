package com.example.mathi.mid_fiprototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Timer extends AppCompatActivity {
    private boolean timerIsRunning;
    private int currentPhase;
    private float timerTime;
    private float recievedTime1;
    private float recievedTime2;
    private float displayTime;
    private TextView timer;
    private String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        init();
        timerIsRunning = true;
        //runTime();
        calcTime();
    }
    public void init(){
        timer = findViewById(R.id.timer);
        recievedTime1 = MainActivity.getNumPickValue1();
        recievedTime2 = MainActivity.getNumPickValue2();
    }
    public void runTime() {
        while (timerIsRunning){
            timer.setText(test);
        }
    }
    public void calcTime(){
        float timer1 = java.lang.System.currentTimeMillis();
        while(true){
            test = Float.toString(timerTime);
            timer.setText(test);
            float timer2 = java.lang.System.currentTimeMillis();
            if(timer2- timer1 == 250){
                timerTime += 0.25;
            }
        }
    }
}
