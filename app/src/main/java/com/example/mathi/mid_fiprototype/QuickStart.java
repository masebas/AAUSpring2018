package com.example.mathi.mid_fiprototype;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
