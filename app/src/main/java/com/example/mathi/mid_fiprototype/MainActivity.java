package com.example.mathi.mid_fiprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
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
    private static NumberPicker numPick1;
    private static NumberPicker numPick2;
    private Button startButton;
    private Button saveButton;
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
        numPick1.setWrapSelectorWheel(true);
        numPick2.setWrapSelectorWheel(true);

        //Initialize startButton and set onClick listener
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runQuickStart();
            }
        });
        //Initialize saveButton and set onClick listener
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveExercise();
            }
        });
    }
    public void runQuickStart(){
        //Runs the Timer.java activity
        Intent startTimer = new Intent(MainActivity.this, Timer.class);
        startActivity(startTimer);
    }
    public void showGridMenu(){
        //Runs the MyExercises.java activity
        Intent showGrid = new Intent(MainActivity.this, MyExercises.class);
        startActivity(showGrid);
    }
    public void saveExercise(){
        DialogFragment dFragment = new SaveDialog();
        dFragment.show(getSupportFragmentManager(), "save");
    }
    public static long getNumPickValue1() {
        //Returns the number value from the first Number Picker
        return numPickValue1;
    }

    public static long getNumPickValue2() {
        //Returns the number value from the second Number Picker
        return numPickValue2;
    }
    public static void setNumPickValue1(long i){
        Long x = i;
        numPick1.setValue(x.intValue());
    }
    public static void setNumPickValue2(long i){
        Long x = i;
        numPick2.setValue(x.intValue());
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
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if(motionEvent.getY() > motionEvent1.getY()){
            showGridMenu();
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
