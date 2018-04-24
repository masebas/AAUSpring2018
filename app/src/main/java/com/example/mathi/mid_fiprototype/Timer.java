package com.example.mathi.mid_fiprototype;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Timer extends AppCompatActivity {
    private long recievedTime1;
    private long recievedTime2;
    private long timeMillis1;
    private long timeMillis2;
    private TextView timer;
    private CountDownTimer countDownTimer;
    private Button mStartButton;
    private Button mStopButton;


    public void init(){
        recievedTime1 = MainActivity.getNumPickValue1();
        recievedTime2 = MainActivity.getNumPickValue2();
        timeMillis1 = recievedTime1 * 1000;
        timeMillis2 = recievedTime2 * 1000;
        timer = findViewById(R.id.timer);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        init();
        calcTime(0);
    }

    @Override
    protected void onStart(){
        super.onStart();

    }

    public void calcTime(int stage){
        timer = findViewById(R.id.timer);
        mStartButton = findViewById(R.id.button_start);
        mStopButton = findViewById(R.id.button_stop);


        if(stage == 0) {
            countDownTimer = new CountDownTimer(2000, 100) {
                @Override
                public void onTick(long l) {
                    timer.setText(Long.toString(l/1000)+ ":" + Long.toString(l/10));
                }

                @Override
                public void onFinish() {
                    calcTime(1);

                }
            };
            countDownTimer.start();
        }
        if(stage == 1){
            countDownTimer = new CountDownTimer(4000, 100) {
                @Override
                public void onTick(long l) {
                    timer.setText(Long.toString(l/1000)+ ":" + Long.toString(l/10));
                }

                @Override
                public void onFinish() {
                    calcTime(0);
                }
            };
            countDownTimer.start();
        }
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
