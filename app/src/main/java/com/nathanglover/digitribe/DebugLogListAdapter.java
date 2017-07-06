package com.nathanglover.digitribe;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DebugLogListAdapter extends ArrayAdapter<DebugDataModel> implements View.OnClickListener {

    private ArrayList<DebugDataModel> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txt_sensor_id;
        TextView txt_sensor_mac;
        TextView txt_location_lon;
        TextView txt_location_lat;
        ImageView status;
    }

    public DebugLogListAdapter(ArrayList<DebugDataModel> data, Context context) {
        super(context, R.layout.debug_row_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        DebugDataModel dataModel = (DebugDataModel)object;

        switch (v.getId())
        {
            case R.id.item_status:
                Snackbar.make(v, "Last Update: " +dataModel.getInfo(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DebugDataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.debug_row_item, parent, false);
            viewHolder.txt_sensor_id = (TextView) convertView.findViewById(R.id.sensor_id);
            viewHolder.txt_sensor_mac = (TextView) convertView.findViewById(R.id.sensor_mac);
            viewHolder.txt_location_lon = (TextView) convertView.findViewById(R.id.location_lon);
            viewHolder.txt_location_lat = (TextView) convertView.findViewById(R.id.location_lat);
            viewHolder.status = (ImageView) convertView.findViewById(R.id.item_status);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txt_sensor_id.setText(dataModel.getSensor_id());
        viewHolder.txt_sensor_mac.setText(dataModel.getSensor_mac());
        viewHolder.txt_location_lon.setText(dataModel.getLocation_lon());
        viewHolder.txt_location_lat.setText(dataModel.getLocation_lat());
        viewHolder.status.setOnClickListener(this);
        viewHolder.status.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
