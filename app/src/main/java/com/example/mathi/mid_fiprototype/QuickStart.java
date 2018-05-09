package com.example.mathi.mid_fiprototype;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class QuickStart extends Fragment {

    private static NumberPicker numPick1;
    private static NumberPicker numPick2;
    private Button startButton;
    private Button saveButton;
    private Switch togVib;
    private Switch togSound;
    private static long numPickValue1;
    private static long numPickValue2;
    private static boolean vibOn;
    private static boolean soundOn;
    private Vibrator vib;

    private static List Exercises;

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String SAVEDEXERCISES = "savedExercises";


    public QuickStart() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.quick_start_layout, null);
        vib = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        init(v);
        return v;
    }

    public void init(View v){
        //Initialize NumberPickers and set min and max values
        numPick1 = v.findViewById(R.id.numPick1);
        numPick2 = v.findViewById(R.id.numPick2);
        numPick1.setMinValue(1);
        numPick1.setMaxValue(2);
        numPick2.setMinValue(1);
        numPick2.setMaxValue(4);
        numPick1.setWrapSelectorWheel(false);
        numPick2.setWrapSelectorWheel(false);

        //Initialize startButton and set onClick listener
        startButton = v.findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runQuickStart();
            }
        });
        //Initialize Toggle buttons
        togVib = v.findViewById(R.id.toggleVibration);
        if(!vibOn){
            togVib.setChecked(false);
        }
        togVib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibOn = togVib.isChecked();
            }
        });

        togSound = v.findViewById(R.id.toggleSound);
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
        //Runs Timer.java
        soundOn = togSound.isChecked();
        vibOn = togVib.isChecked();

        Bundle extras = new Bundle();
        extras.putLong("time1", numPick1.getValue());
        extras.putLong("time2", numPick2.getValue());
        extras.putBoolean("soundOn", soundOn);
        extras.putBoolean("vibOn", vibOn);
        Intent startTimer = new Intent(getContext(), Timer.class);
        startTimer.putExtras(extras);
        startActivity(startTimer);
    }
    public static void saveExercise(){
        if(MainActivity.getInstance()!=null){
            final Dialog dialog = new Dialog(MainActivity.getInstance());
            dialog.setContentView(R.layout.alert_layout);
            dialog.setTitle("Add Exercise");
            Button confirm = dialog.findViewById(R.id.buttonConfirm);
            Button cancel = dialog.findViewById(R.id.buttonCancel);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText text = dialog.findViewById(R.id.nameid);
                    Exercise e = new Exercise(text.getText().toString(), numPick1.getValue(), numPick2.getValue(), soundOn, vibOn);
                    Exercises.add(e);
                    saveData(e);
                    MyExercises.newExercise(e);
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

    }
    private static void saveData(Exercise e){
        SharedPreferences sharedPreferences = MainActivity.getInstance().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.clear();
        Gson gson = new Gson();
        String json = gson.toJson(Exercises);
        edit.putString("exerciseList", json);
        edit.apply();
    }
    public static void loadData(){
        SharedPreferences sharedPreferences = MainActivity.getInstance().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("exerciseList", null);
        Type type = new TypeToken<ArrayList<Exercise>>() {}.getType();
        Exercises = gson.fromJson(json, type);

        if(json == null){
            Exercises = new ArrayList<Exercise>();
        }
    }
    public static void passLoadedData(){
        for(int i = 0; i < Exercises.size(); i++){
            Exercise e = (Exercise) Exercises.get(i);
            MyExercises.newExercise(e);
        }
    }
    public static long getNumPickValue1() {
        return numPickValue1;
    }

    public static long getNumPickValue2() {
        return numPickValue2;
    }
    public static boolean isSoundOn(){
        return soundOn;
    }
    public static boolean isVibOn(){
        return vibOn;
    }
    public static View loadView(Fragment fragment){
        LayoutInflater inflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.quick_start_layout, null);
        return v;
    }
}
