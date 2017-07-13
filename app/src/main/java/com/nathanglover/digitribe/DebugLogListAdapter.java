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

public class DebugLogListAdapter extends ArrayAdapter<DataPointModel> implements View.OnClickListener {

    private ArrayList<DataPointModel> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txt_sensor_id;
        TextView txt_sensor_mac;
        TextView txt_sensor_altitude;
        TextView txt_sensor_velocity;
        TextView txt_sensor_gps_error;
        TextView txt_sensor_imu_error;
        TextView txt_sensor_rightdirection;
        TextView txt_sensor_course;

        TextView txt_location_lon;
        TextView txt_location_lat;
        TextView txt_sensor_nsats;
        TextView txt_sensor_snr1;
        TextView txt_sensor_snr2;
        TextView txt_sensor_snr3;
        TextView txt_sensor_snr4;

        ImageView status;
    }

    public DebugLogListAdapter(ArrayList<DataPointModel> data, Context context) {
        super(context, R.layout.debug_row_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        DataPointModel dataModel = (DataPointModel)object;

        switch (v.getId())
        {
            case R.id.item_status:
                Snackbar.make(v, "Last Sensor Update: " +dataModel.getTimestamp() +"\nGPS Datestamp: " +dataModel.getDatestamp(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataPointModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.debug_row_item, parent, false);
            viewHolder.txt_sensor_id = (TextView) convertView.findViewById(R.id.sensor_id);
            viewHolder.txt_sensor_mac = (TextView) convertView.findViewById(R.id.sensor_mac);
            viewHolder.txt_sensor_altitude = (TextView) convertView.findViewById(R.id.sensor_altitude);
            viewHolder.txt_sensor_velocity = (TextView) convertView.findViewById(R.id.sensor_velocity);
            viewHolder.txt_sensor_gps_error = (TextView) convertView.findViewById(R.id.sensor_gps_error);
            viewHolder.txt_sensor_imu_error = (TextView) convertView.findViewById(R.id.sensor_imu_error);
            viewHolder.txt_sensor_rightdirection = (TextView) convertView.findViewById(R.id.sensor_rightdirection);
            viewHolder.txt_sensor_course = (TextView) convertView.findViewById(R.id.sensor_course);

            viewHolder.txt_location_lon = (TextView) convertView.findViewById(R.id.location_lon);
            viewHolder.txt_location_lat = (TextView) convertView.findViewById(R.id.location_lat);
            viewHolder.txt_sensor_nsats = (TextView) convertView.findViewById(R.id.sensor_nsats);
            viewHolder.txt_sensor_snr1 = (TextView) convertView.findViewById(R.id.sensor_snr1);
            viewHolder.txt_sensor_snr2 = (TextView) convertView.findViewById(R.id.sensor_snr2);
            viewHolder.txt_sensor_snr3 = (TextView) convertView.findViewById(R.id.sensor_snr3);
            viewHolder.txt_sensor_snr4 = (TextView) convertView.findViewById(R.id.sensor_snr4);

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
        viewHolder.txt_sensor_altitude.setText(String.valueOf(dataModel.getAltitude()));
        viewHolder.txt_sensor_velocity.setText(String.valueOf(dataModel.getVelocity()));
        viewHolder.txt_sensor_gps_error.setText(String.valueOf(dataModel.getGPSerror()));
        viewHolder.txt_sensor_imu_error.setText(String.valueOf(dataModel.getIMUerror()));
        viewHolder.txt_sensor_rightdirection.setText(String.valueOf(dataModel.getRightDirection()));
        viewHolder.txt_sensor_course.setText(String.valueOf(dataModel.getCourse()));

        viewHolder.txt_location_lon.setText(String.valueOf(dataModel.getLocation_lon()));
        viewHolder.txt_location_lat.setText(String.valueOf(dataModel.getLocation_lat()));
        viewHolder.txt_sensor_nsats.setText(String.valueOf(dataModel.getNumbersats()));
        viewHolder.txt_sensor_snr1.setText(String.valueOf(dataModel.getSNR1()));
        viewHolder.txt_sensor_snr2.setText(String.valueOf(dataModel.getSNR2()));
        viewHolder.txt_sensor_snr3.setText(String.valueOf(dataModel.getSNR3()));
        viewHolder.txt_sensor_snr4.setText(String.valueOf(dataModel.getSNR4()));

        viewHolder.status.setOnClickListener(this);
        viewHolder.status.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
