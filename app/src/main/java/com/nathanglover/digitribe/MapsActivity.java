package com.nathanglover.digitribe;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends MainActivity implements OnMapReadyCallback {

    private String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap googleMap;

    private ArrayList<DataPointModel> dataPointList;

    @Override
    int getContentViewId() {
        return R.layout.activity_maps;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_map;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        dataPointList = new ArrayList<>();
        new GetNodeData().execute();

        /*
        LatLng perth_core = new LatLng(-31.9538, 115.8532);
        LatLng perth_sap = new LatLng(-31.953494, 115.8540433);

        // create markers
        MarkerOptions perth_core_marker = new MarkerOptions().position(
                perth_core).title("Perth CORE");
        MarkerOptions perth_sap_marker = new MarkerOptions().position(
                perth_sap).title("Perth SAP");

        Circle circle = map.addCircle(new CircleOptions()
                .center(perth_core)
                .radius(300)
                .strokeWidth(5)
                .strokeColor(Color.RED)
                .fillColor(Color.argb(128, 255, 0, 0))
                .clickable(true));

        // Changing marker icons
        perth_core_marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        perth_sap_marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        // adding markers
        googleMap.addMarker(perth_core_marker);
        googleMap.addMarker(perth_sap_marker);
        */

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(-31.953494, 115.8540433)).zoom(10).build();

        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }

    private class GetNodeData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MapsActivity.this,"Json GetNodeData is downloading",Toast.LENGTH_LONG).show();

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
                        String datestamp = n.getString("datestamp");
                        double altitude = n.getDouble("velocity");
                        Boolean GPSerror = n.getBoolean("GPSerror");
                        Boolean IMUerror = n.getBoolean("IMUerror");
                        Boolean rightdirection = n.getBoolean("rightdirection");
                        double course = n.getDouble("course");
                        double nsats = n.getDouble("nsats");
                        Integer SNR1 = n.getInteger("SNR1");
                        Integer SNR2 = n.getInteger("SNR2");
                        Integer SNR3 = n.getInteger("SNR3");
                        Integer SNR4 = n.getInteger("SNR4");
                    }
     
                        DataPointModel point = new DataPointModel(
                                sensor_id,
                                sensor_mac,
                                location_lon,
                                location_lat,
                                timestamp,
                                datestamp,
                                altitude,
                                velocity,
                                GPSerror,
                                IMUerror,
                                rightdirection,
                                course,
                                nsats,
                                SNR1,
                                SNR2,
                                SNR3,
                                SNR4
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

            for (DataPointModel point : dataPointList) {
                // Add market for each option
                MarkerOptions mo = new MarkerOptions().position(
                        new LatLng(
                                point.getLocation_lat(),
                                point.getLocation_lon()
                        )).title(point.getSensor_id()
                );
                // Add market to map
                googleMap.addMarker(mo);
            }
        }
    }
}
