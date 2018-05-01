package com.example.mathi.mid_fiprototype;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.GestureDetector;
import android.view.LayoutInflater;
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
    private static MainActivity instance = null;
    private Context context;
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
    private BottomNavigationView navigation;
    private GestureDetectorCompat gDetector;
    private Fragment fragmentQuickStart = new QuickStart();
    private Fragment fragmentMyExercises = new MyExercises();
    private Fragment fragmentAboutTut = new fragmentAboutTut();
    private Fragment fragmentSettings = new fragmentSettings();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_quick_start:
                    return loadFragment(fragmentQuickStart);
                case R.id.navigation_exercises:
                    return loadFragment(fragmentMyExercises);
                case R.id.navigation_save:
                    if(navigation.getSelectedItemId() == R.id.navigation_quick_start || navigation.getSelectedItemId() == R.id.navigation_save) {
                        QuickStart.saveExercise();
                        return false;
                    } else {
                        navigation.setSelectedItemId(R.id.navigation_quick_start);
                        return loadFragment(fragmentQuickStart);
                    }
                case R.id.navigation_about_tut:
                    return loadFragment(fragmentAboutTut);
                case R.id.navigation_settings:
                    return loadFragment(fragmentSettings);
            }
            return loadFragment(fragment);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        this.instance = this;
    }
    public static MainActivity getInstance(){
        return instance;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        vib.cancel();
    }
    public boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
            return true;

        }
        return false;
    }

    public void init(){
        loadFragment(new QuickStart());
        //Initialize BottomNavigationView
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Initialize GestureDetector
        gDetector = new GestureDetectorCompat(this, this);
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
        navigation.setSelectedItemId(R.id.navigation_exercises);
        loadFragment(new MyExercises());

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
        if(motionEvent.getY() < motionEvent1.getY()){
            navigation.setSelectedItemId(R.id.navigation_quick_start);
            loadFragment(fragmentQuickStart);
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
