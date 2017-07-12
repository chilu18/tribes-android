package com.nathanglover.digitribe;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.jinatonic.confetti.CommonConfetti;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DebugActivity extends MainActivity {

    private String TAG = DebugActivity.class.getSimpleName();
    private ArrayList<DataPointModel> dataPointList;

    private ListView listView;
    private DebugLogListAdapter adapter;

    private MediaPlayer mediaPlayer;
    private ImageView imageView;
    private Animation rotateAnimation;

    @Override
    int getContentViewId() {
        return R.layout.activity_debug;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_debug;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Log list logic
        listView=(ListView)findViewById(R.id.log_list);
        dataPointList= new ArrayList<>();

        // Populate Log list here
        new GetNodeData().execute();

        adapter= new DebugLogListAdapter(dataPointList,getApplicationContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataPointModel dataModel= dataPointList.get(position);

                Snackbar.make(view, dataModel.getSensor_id()+ " Last Updated: " +dataModel.getTimestamp(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });

        // Image related logic
        mediaPlayer = MediaPlayer.create(this, R.raw.danger);
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotator);
        imageView = (ImageView)findViewById(R.id.kenny_loggins_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                rotateAnimation.setDuration(900);
                imageView.startAnimation(rotateAnimation);
                CommonConfetti.rainingConfetti(container, colors)
                        .infinite();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    private class GetNodeData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(DebugActivity.this,"logs loading...",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://dt.nathanglover.com/api/v1/data";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray node_data = jsonObj.getJSONArray("data");

                    // looping through All Data points
                    for (int i = 0; i < node_data.length(); i++) {
                        JSONObject n = node_data.getJSONObject(i);
                        String sensor_id = n.getString("sensor_id");
                        String sensor_mac = n.getString("sensor_mac");
                        double location_lon = n.getDouble("location_lon");
                        double location_lat = n.getDouble("location_lat");
                        String timestamp = n.getString("timestamp");

                        DataPointModel point = new DataPointModel(
                                sensor_id,
                                sensor_mac,
                                location_lon,
                                location_lat,
                                timestamp
                        );

                        // adding point to data point list
                        dataPointList.add(point);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


        }
    }
}
