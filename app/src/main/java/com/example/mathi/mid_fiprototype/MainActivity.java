package com.example.mathi.mid_fiprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    private TextView mTextMessage;
    private GridView gridView;
    int iconsList[] = {R.drawable.icon,

    };
    String valuesList[] = {" ", " ", " ", " " };
    private NumberPicker numPick1;
    private NumberPicker numPick2;
    private Button startButton;
    private static long numPickValue1;
    private static long numPickValue2;
    public String textView = "Quick Start";

    private GestureDetectorCompat gDetector;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }
    public void init(){
        //Initialize gridView for saved exercise profiles
        gridView = findViewById(R.id.grid);
        GridAdapter gridAdapter = new GridAdapter(MainActivity.this, iconsList, valuesList );

        //Initialize BottomNavigationView
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Initialize GestureDetector
        gDetector = new GestureDetectorCompat(this, this);

        //Initialize NumberPickers and set min and max values
        numPick1 = findViewById(R.id.numPick1);
        numPick2 = findViewById(R.id.numPick2);
        numPick1.setMinValue(1);
        numPick1.setMaxValue(2);
        numPick2.setMinValue(1);
        numPick2.setMaxValue(4);

        //Initialize startButton and set onClick listener
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runQuickStart();
            }
        });
    }
    public void runQuickStart(){
        Intent startTimer = new Intent(MainActivity.this, Timer.class);
        startActivity(startTimer);
    }
    public static long getNumPickValue1() {
        return numPickValue1;
    }

    public static long getNumPickValue2() {
        return numPickValue2;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if(motionEvent.getY ()< motionEvent1.getY()){
            numPickValue1 = numPick1.getValue();
            numPickValue2 = numPick2.getValue();
            return false;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        return;
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
