package com.zeleznick.represent;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RepAdapter extends ArrayAdapter<Representative> {

    Context context;
    int layoutResourceId;
    Representative data[] = null;
    ArrayList<Float> raw_values = new ArrayList<>();


    public RepAdapter(Context context, int layoutResourceId, Representative[] data, ArrayList<Float> raw_values ) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.raw_values = raw_values;
    }

    public void update(int selected, String exercise, int inputValue){
        for (int i=0; i<data.length; i++){
            float floatValue = (raw_values.get(i) / raw_values.get(selected)) * inputValue;;
            // Log.i("value", "pre-cast value is: " + floatValue);
            int value = (int) floatValue;
            // Log.i("value", "post-cast value is: " + value);
            String label;
            if (i == 0) {
                label = "Calories";
            }
            else if(i>2) {
                label = "minutes";
            }
            else {
                label = "reps";
            }
            // data[i].title = Integer.toString(value) + "  " + label;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        exHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new exHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            row.setTag(holder);
        }
        else
        {
            holder = (exHolder)row.getTag();
        }

        Representative rep = data[position];
        Log.i("get_rep", "Get Rep Called with position: " + position);
        Log.i("rep", "REP: " + rep.firstName);
        String title = rep.firstName + " " + rep.lastName;
        holder.txtTitle.setText(title);
        holder.imgIcon.setImageResource(rep.icon);

        return row;
    }

    static class exHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}