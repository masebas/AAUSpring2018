package com.example.mathi.mid_fiprototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MyExercises extends AppCompatActivity implements GestureDetector.OnGestureListener{

    private static List<Integer> iconsList = new ArrayList<Integer>();
    private static List<String> valuesList = new ArrayList<String>();
    private static List<Exercise> exerciseList = new ArrayList<Exercise>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_exercises);

        GridView grid = (GridView) findViewById(R.id.grid);
        GridAdapter adapter = new GridAdapter(MyExercises.this, iconsList, valuesList);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                MainActivity.setNumPickValue1(exerciseList.get(position).getValue1());
                MainActivity.setNumPickValue2(exerciseList.get(position).getValue2());
                Intent startTimer = new Intent(MyExercises.this, Timer.class);
                startActivity(startTimer);
            }
        });

    }

    public static void newExercise(Exercise e){
        valuesList.add(e.getName());
        iconsList.add(R.drawable.icon);
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
