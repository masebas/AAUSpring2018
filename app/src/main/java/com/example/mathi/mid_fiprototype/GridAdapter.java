package com.example.mathi.mid_fiprototype;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter{

    private Context mContext;
    private List<String> values = new ArrayList<String>();
    private List<Integer> icons = new ArrayList<Integer>();
    private List<String> eccTime = new ArrayList<String>();
    private List<String> conTime = new ArrayList<String>();
    private List<String> soundCheck = new ArrayList<String>();
    private List<String> vibCheck = new ArrayList<String>();
    private LayoutInflater layoutInflater;

    public GridAdapter(Context mContext, List<Integer> icons, List<String> values, List<String> eccTime, List<String> conTime, List<String> soundCheck, List<String> vibCheck){
        this.mContext = mContext;
        this.icons = icons;
        this.values = values;
        this.conTime = conTime;
        this.eccTime = eccTime;
        this.soundCheck = soundCheck;
        this.vibCheck = vibCheck;

    }


    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View gridView = convertView;

        if(convertView == null){
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = layoutInflater.inflate(R.layout.custom_layout, null);
        }
        ImageView icon = (ImageView) gridView.findViewById(R.id.icons);
        TextView value = (TextView) gridView.findViewById(R.id.values);
        TextView tConTime = (TextView) gridView.findViewById(R.id.conTime);
        TextView tEccTime = (TextView) gridView.findViewById(R.id.eccTime);
        TextView tSoundCheck = (TextView) gridView.findViewById(R.id.soundCheck);
        TextView tVibCheck = (TextView) gridView.findViewById(R.id.vibCheck);

        icon.setImageResource(icons.get(position));
        value.setText(values.get(position));
        tConTime.setText("Concentric: " + conTime.get(position));
        tEccTime.setText("Eccentric: " + eccTime.get(position));
        tSoundCheck.setText("Sound: " + soundCheck.get(position));
        tVibCheck.setText("Vibration: " + vibCheck.get(position));

        return gridView;

    }

    public List<String> getEccTime() {
        return eccTime;
    }

    public void setEccTime(List<String> eccTime) {
        this.eccTime = eccTime;
    }

    public List<String> getConTime() {
        return conTime;
    }

    public void setConTime(List<String> conTime) {
        this.conTime = conTime;
    }

    public List<String> getSoundCheck() {
        return soundCheck;
    }

    public void setSoundCheck(List<String> soundCheck) {
        this.soundCheck = soundCheck;
    }

    public List<String> getVibCheck() {
        return vibCheck;
    }

    public void setVibCheck(List<String> vibCheck) {
        this.vibCheck = vibCheck;
    }
}
