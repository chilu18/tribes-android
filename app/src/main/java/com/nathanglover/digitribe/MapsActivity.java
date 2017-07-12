package com.nathanglover.digitribe;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class MapsActivity extends MainActivity
        implements
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        LocationListener {

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;

    private String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private LocationManager locationManager;
    private String provider;

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
        mMap = map;
        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();
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
    }

    @Override
    public boolean onMyLocationButtonClick() {
        //Toast.makeText(this, "Locating...", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }

        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabledGPS = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean enabledWiFi = service
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!enabledGPS) {
            Toast.makeText(MapsActivity.this, "GPS signal not found", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        } else if (!enabledWiFi) {
            Toast.makeText(MapsActivity.this, "Network signal not found", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the location provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            onLocationChanged(location);
        } else {
            //do something
        }
        setUpMap();
    }

    private void setUpMap() {
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setPadding(0,0,0,300);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "Location permissions are missing. Please resolve.");
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat =  location.getLatitude();
        double lng = location.getLongitude();
        LatLng coordinate = new LatLng(lat, lng);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 15.0f));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class GetNodeData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MapsActivity.this,"Syncing...",Toast.LENGTH_LONG).show();
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

            // For generating random radias
            Random rand = new Random();
            int min = 100;
            int max = 500;

            for (DataPointModel point : dataPointList) {
                // Add market for each option
                MarkerOptions mo = new MarkerOptions().position(
                        new LatLng(
                                point.getLocation_lat(),
                                point.getLocation_lon()
                        )).title(point.getSensor_id()
                );

                Circle circle = mMap.addCircle(new CircleOptions()
                        .center(new LatLng(
                                point.getLocation_lat(),
                                point.getLocation_lon()
                        ))
                        .radius(rand.nextInt(max - min + 1) + 1)
                        .strokeWidth(5)
                        .strokeColor(Color.RED)
                        .fillColor(Color.argb(128, 255, 0, 0))
                );

                // Add market to map
                mMap.addMarker(mo);
            }
        }
    }
}
