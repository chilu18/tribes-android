package com.nathanglover.digitribe;

public class DataPointModel {

    private String sensor_id;
    private String sensor_mac;
    private double location_lon;
    private double location_lat;
    private String timestamp;

    public DataPointModel(String sensor_id, String sensor_mac, double location_lon, double location_lat, String timestamp) {
        this.sensor_id = sensor_id;
        this.sensor_mac = sensor_mac;
        this.location_lon = location_lon;
        this.location_lat = location_lat;
        this.timestamp = timestamp;
    }

    public String getSensor_id() {
        return sensor_id;
    }

    public String getSensor_mac() {
        return sensor_mac;
    }

    public double getLocation_lon() {
        return location_lon;
    }

    public double getLocation_lat() {
        return location_lat;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
