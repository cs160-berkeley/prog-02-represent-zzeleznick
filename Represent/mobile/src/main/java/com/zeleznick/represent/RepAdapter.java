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

public class RepAdapter extends ArrayAdapter<Representative> {

    Context context;
    int layoutResourceId;
    Representative data[] = null;

    public RepAdapter(Context context, int layoutResourceId, Representative[] data ) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
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
            holder.repName = (TextView)row.findViewById(R.id.repName);
            holder.party = (TextView)row.findViewById(R.id.party);
            holder.email = (TextView)row.findViewById(R.id.email);
            holder.twitterHandle = (TextView)row.findViewById(R.id.twitterHandle);
            holder.website = (TextView)row.findViewById(R.id.website);
            row.setTag(holder);
        }
        else
        {
            holder = (exHolder)row.getTag();
        }

        Representative rep = data[position];
        Log.i("get_rep", "Get Rep Called with position: " + position);
        Log.i("rep", "REP: " + rep.firstName);
        String fullName = rep.firstName + " " + rep.lastName;
        holder.imgIcon.setImageResource(rep.icon);
        holder.repName.setText(fullName);
        holder.party.setText(rep.party);
        holder.email.setText(rep.email);
        holder.twitterHandle.setText(rep.twitterHandle);
        holder.website.setText(rep.website);

        return row;
    }

    static class exHolder
    {
        ImageView imgIcon; // column 1
        TextView repName; // column 2
        TextView party;
        TextView email;
        TextView twitterHandle; // column 3
        TextView website;
    }
}