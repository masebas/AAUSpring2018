package com.example.mathi.mid_fiprototype;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter{

    private Context mContext;
    private List<String> values = new ArrayList<String>();
    private List<Integer> icons = new ArrayList<Integer>();
    private LayoutInflater layoutInflater;

    public GridAdapter(Context mContext, List<Integer> icons, List<String> values){
        this.mContext = mContext;
        this.icons = icons;
        this.values = values;


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
        icon.setImageResource(icons.get(position));
        value.setText(values.get(position));

        return gridView;

    }
}
