package com.example.mathi.mid_fiprototype;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridAdapter extends BaseAdapter{

    private Context mContext;
    private String[] values[];
    private int icons[];
    private LayoutInflater layoutInflater;

    public GridAdapter(Context mContext, int icons[], String values[]){
        this.mContext = mContext;
        this.icons = icons;
        this.values = values;


    }


    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int position) {
        return values[position];
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
        ImageView icon = (ImageView) gridView.findViewById(R.id.imageView);

        icon.setImageResource(icons[position]);

        return gridView;

    }
}
