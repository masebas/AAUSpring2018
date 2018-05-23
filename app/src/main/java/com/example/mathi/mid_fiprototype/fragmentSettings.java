package com.example.mathi.mid_fiprototype;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class fragmentSettings extends Fragment {
    //Declare Variables
    private SeekBar volumeBar;
    private SeekBar vibBar;
    private Switch toggleSound;
    private Switch toggleVib;
    private Button resetButton;
    private NumberPicker timerBuffer;
    private AudioManager mAudioManager;

    //Declare and Initialize constants
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String VOLUME = "volume";
    private static final String VIBRATION = "vibration";
    private static final String BUFFER = "buffer";
    private static final String DEFAULT_SOUND = "defaultSound";
    private static final String DEFAULT_VIBRATION = "defaultVib";

    public fragmentSettings(){
        //Required empty constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_settings, container, false);
        init(v);                                                                                    //Passes the view to the initializer
        loadSettings();                                                                             //Loads the saved settings from SharedPreferences
        passData();                                                                                 //Passes data to the appropriate variables and classes

        return v;                                                                                   //Returns the view to the onCreateView method
    }

    public void init(View v){
        //Initializes the UI objects
        mAudioManager = (AudioManager) MainActivity                                                 //Initializes the AudioManager from the current
                .getInstance()                                                                      //instance of Main Activity.
                .getSystemService(Context.AUDIO_SERVICE);                                           //This is necessary because we're working in a fragment

        volumeBar = v.findViewById(R.id.volume);                                                    //Initializes the Volume SeekBar from the passed view
        vibBar = v.findViewById(R.id.vibration);                                                    //Initializes the Vibration SeekBar from the passed view
        timerBuffer = v.findViewById(R.id.numPickBuffer);                                           //Initializes the Buffer NumberPicker from the passed view
        toggleSound = v.findViewById(R.id.defaultSound);                                            //Initializes the defaultSound switch from the passed view
        //toggleVib = newView.findViewById(R.id.vibrationSwitch);                                                 //Initializes the default Vibration switch from the passed view (CURRENTLY NOT WORKING)


        volumeBar.setMax(100);                                                                      //Sets the max value for the volume SeekBar to 100
            volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {            //Instantiates a new onChangeListener for the Volume SeekBar
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {                      //Method called when a change happens to the SeekBar Slider
                mAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, i, 0);               //Sets the Stream Volume of the Music Stream to i, which is the current SeekBar Progress

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {                                      //Called when the user stops interacting with the SeekBar
                saveSettings();                                                                     //Saves the current settings state to Shared Preferences
            }
        });

        vibBar.setMax(254);                                                                         //Sets the max value for the vibration SeekBar to 254.
                                                                                                    //Although the max value for vibration intensity is 255,
                                                                                                    //setting the number to 255 seems buggy
        vibBar.setMin(1);                                                                           //Sets the min value for the vibration SeekBar to 1. Setting it to 0 seems buggy
        vibBar.setProgress(128);                                                                    //Sets the default progress of vibration intensity if none is loaded from SharedPreferences
        vibBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {                   //Instantiates a new onChangeListener
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Timer.setVibAmp(i);                                                                 //Sets the vibration amplitude of the vibrator called in the timer activity to i

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                saveSettings();                                                                     //Saves the settings current state to SharedPreferences
            }
        });

        timerBuffer.setMaxValue(30);                                                                //Sets the max value of the Buffer Number Picker
        timerBuffer.setMinValue(0);                                                                 //Sets the min value of the Buffer Number Picker
        timerBuffer.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {            //Instantiates a new onChangeListener
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {                   //Method called on value changed
                saveSettings();                                                                     //Saves the settings current state to SharedPreferences
            }
        });

        toggleSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {       //Instantiates a new onChangeListener
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {                //Method called when Switch state is changed
                saveSettings();                                                                     //Saves the settings current state to SharedPreferences
            }
        });



    }

    public void saveSettings(){ //Saves the settings current state to SharedPreferences
        SharedPreferences sharedPreferences = MainActivity                                          //Instantiates an instance of SharedPreferences
                .getInstance()                                                                      //from the current instance of MainActivity.
                .getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);                                  // This is necessary because we're working in a fragment

        SharedPreferences.Editor edit = sharedPreferences.edit();                                   //Instantiates an instance of SharedPreferences.Editor
        edit.putInt(VOLUME, mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));              //Saves the current Volume settings in the VOLUME constant in SharedPreferences
        edit.putInt(VIBRATION, vibBar.getProgress());                                               //Saves the current Vibration state in the VIBRATION constant in SharedPreferences
        edit.putInt(BUFFER, timerBuffer.getValue());                                                //Saves the current Buffer value in the BUFFER constant in SharedPreferences
        edit.putBoolean(DEFAULT_SOUND, toggleSound.isChecked());                                    //Saves the current default sound toggle state in the DEFAULT_SOUND constant in SharedPreferences
        //edit.putBoolean(DEFAULT_VIBRATION, toggleVib.isChecked());                                //Currently not working

        edit.apply();                                                                               //Applies the changes to SharedPreferences
        passData();                                                                                 //Passes the saved data to the appropriate variables and classes
    }
    public void loadSettings(){                                                                     //Loads the saved state of SharedPreferences
        SharedPreferences sharedPreferences = MainActivity                                          //Instantiates an instance of SharedPreferences
                .getInstance()                                                                      //with the same constant name as was used in the
                .getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);                                  //saveSettings method

        volumeBar.setProgress(sharedPreferences.getInt(VOLUME, 50));                             //Loads the volume value from the VOLUME constant
        vibBar.setProgress(sharedPreferences.getInt(VIBRATION, 128));                            //Loads the vibration value from the VIBRATION constant
        timerBuffer.setValue(sharedPreferences.getInt(BUFFER, 0));                               //Loads the buffer value from the BUFFER constant
        toggleSound.setChecked(sharedPreferences.getBoolean(DEFAULT_SOUND, false));             //Loads the default Sound setting from the DEFAULT_SOUND constant
        //toggleVib.setChecked(sharedPreferences.getBoolean(DEFAULT_VIBRATION, false));             //Currently not working

    }
    public void passData(){
        Timer.setVibAmp(vibBar.getProgress());                                                      //Passes the Vibration value to the vibAmp variable in the Timer class
        Timer.setBuffer(timerBuffer.getValue());                                                    //Passes the Buffer value to the buffer variable in the Timer class
        QuickStart.setSoundOn(toggleSound.isChecked());                                             //Passes the toggleSound value to the QuickStart fragment
        //QuickStart.setVibOn(toggleVib.isChecked());                                               //Currently not working
    }
}
