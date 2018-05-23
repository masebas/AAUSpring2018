package com.example.mathi.mid_fiprototype;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MyExercises extends Fragment implements GestureDetector.OnGestureListener{

    private static List<Integer> iconsList = new ArrayList<Integer>();
    private static List<String> valuesList = new ArrayList<String>();
    private static List<Exercise> exerciseList = new ArrayList<Exercise>();
    private static List<String> eccTime = new ArrayList<String>();
    private static List<String> conTime = new ArrayList<String>();
    private static List<String> soundCheck = new ArrayList<String>();
    private static List<String> vibCheck = new ArrayList<String>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_exercises, null);
        GridView grid =  view.findViewById(R.id.grid);
        GridAdapter adapter = new GridAdapter(getContext(), iconsList, valuesList, eccTime, conTime, soundCheck, vibCheck);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Bundle extras = new Bundle();
                extras.putLong("time1", exerciseList.get(position).getValue1());
                extras.putLong("time2", exerciseList.get(position).getValue2());
                extras.putBoolean("soundOn", exerciseList.get(position).isSound());
                extras.putBoolean("vibOn", exerciseList.get(position).isVibration());
                Intent startTimer = new Intent(getContext(), Timer.class);
                startTimer.putExtras(extras);
                startActivity(startTimer);
            }
        });
            return view;
    }
    public static void newExercise(Exercise e){
        valuesList.add(e.getName());
        iconsList.add(R.drawable.icon);
        eccTime.add(Long.toString(e.getValue2()));
        conTime.add(Long.toString(e.getValue1()));
        if(e.isSound()){
            soundCheck.add("On");
        } else if(!e.isSound()){
            soundCheck.add("Off");
        }
        if(e.isVibration()){
            vibCheck.add("On");
        } else if(!e.isVibration()){
            vibCheck.add("Off");
        }
        exerciseList.add(e);
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
        return false;
    }
}
