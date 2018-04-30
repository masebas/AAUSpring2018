package com.example.mathi.mid_fiprototype;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
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
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.GridView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    private TextView mTextMessage;
    private static NumberPicker numPick1;
    private static NumberPicker numPick2;
    private Button startButton;
    private Button saveButton;
    private Switch togVib;
    private Switch togSound;
    private static long numPickValue1;
    private static long numPickValue2;
    private boolean vibOn;
    private boolean soundOn;
    private Vibrator vib;
    private BottomNavigationView bottomNavigationView;
    private GestureDetectorCompat gDetector;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_quick_start:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_exercises:
                    showGridMenu();
                    return true;
                case R.id.navigation_save:
                    //mTextMessage.setText(R.string.save_exercise);
                    saveExercise();
                    return false;
                case R.id.navigation_about_tut:
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_settings:
                    //mTextMessage.setText(R.string.quick_start);
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
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        bottomNavigationView = findViewById(R.id.navigation);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        vib.cancel();
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
        numPick1.setWrapSelectorWheel(false);
        numPick2.setWrapSelectorWheel(false);

        //Initialize startButton and set onClick listener
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runQuickStart();
            }
        });
        //Initialize Toggle buttons
        togVib = findViewById(R.id.toggleVibration);
        if(!vibOn){
        togVib.setChecked(false);
        }
        togVib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibOn = togVib.isChecked();
            }
        });

        togSound = findViewById(R.id.toggleSound);
        if(!soundOn){
            togSound.setChecked(false);
        }
        togSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundOn = togSound.isChecked();
            }
        });
    }
    public void runQuickStart(){
        //Runs the Timer.java activity
        soundOn = togSound.isChecked();
        vibOn = togVib.isChecked();

        Bundle extras = new Bundle();
        extras.putLong("time1", numPick1.getValue());
        extras.putLong("time2", numPick2.getValue());
        extras.putBoolean("soundOn", soundOn);
        extras.putBoolean("vibOn", vibOn);
        Intent startTimer = new Intent(MainActivity.this, Timer.class);
        startTimer.putExtras(extras);
        startActivity(startTimer);
    }
    public void showGridMenu(){
        //Runs the MyExercises.java activity
        Intent showGrid = new Intent(MainActivity.this, MyExercises.class);
        startActivity(showGrid);
    }
    public void saveExercise(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.alert_layout);
        dialog.setTitle("Add Exercise");

        Button confirm = dialog.findViewById(R.id.buttonConfirm);
        Button cancel = dialog.findViewById(R.id.buttonCancel);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text = dialog.findViewById(R.id.nameid);
                Exercise e = new Exercise(text.getText().toString(), numPick1.getValue(), numPick2.getValue(), soundOn, vibOn);
                MyExercises.newExercise(e);
                bottomNavigationView.setSelectedItemId(R.id.navigation_quick_start);
                dialog.hide();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
        dialog.show();
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
    public static void setNumPickValue1(long numPickValue1) {
        MainActivity.numPickValue1 = numPickValue1;
    }

    public static void setNumPickValue2(long numPickValue2) {
        MainActivity.numPickValue2 = numPickValue2;
    }

}
