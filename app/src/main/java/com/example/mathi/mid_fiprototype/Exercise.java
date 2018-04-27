package com.example.mathi.mid_fiprototype;


public class Exercise {
    private String name;
    private long value1;
    private long value2;
    private boolean sound;
    private boolean vibration;

    public Exercise(String name, long value1, long value2, boolean sound, boolean vibration){
        this.vibration = vibration;
        this.sound = sound;
        this.name = name;
        this.value1 = value1;
        this.value2 = value2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getValue1() {
        return value1;
    }

    public void setValue1(long value1) {
        this.value1 = value1;
    }

    public long getValue2() {
        return value2;
    }

    public void setValue2(long value2) {
        this.value2 = value2;
    }

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public boolean isVibration() {
        return vibration;
    }

    public void setVibration(boolean vibration) {
        this.vibration = vibration;
    }
}
