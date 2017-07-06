package com.nathanglover.digitribe;

public class DebugDataModel {

    private String sensor_id;
    private String sensor_mac;
    private double location_lon;
    private double location_lat;
    private String info;

    public DebugDataModel(String sensor_id, String sensor_mac, double location_lon, double location_lat, String info) {
        this.sensor_id = sensor_id;
        this.sensor_mac = sensor_mac;
        this.location_lon = location_lon;
        this.location_lat = location_lat;
        this.info = info;
    }

    public String getSensor_id() {
        return sensor_id;
    }

    public String getSensor_mac() {
        return sensor_mac;
    }

    public String getLocation_lon() {
        return String.valueOf(location_lon);
    }

    public String getLocation_lat() {
        return String.valueOf(location_lat);
    }

    public String getLocation() {
        return "(" + location_lon + "," + location_lat +")";
    }

    public String getInfo() {
        return info;
    }
}
