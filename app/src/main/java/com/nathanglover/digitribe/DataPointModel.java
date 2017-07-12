package com.nathanglover.digitribe;

public class DataPointModel {

    private String sensor_id;
    private String sensor_mac;
    private double location_lon;
    private double location_lat;
    private String timestamp;
    private String datestamp;
    private double altitude;
    private double velocity;
    private Boolean GPSerror;
    private Boolean IMUerror;
    private Boolean rightdirection;
    private double course;
    private double nsats;
    private Integer SNR1;
    private Integer SNR2;
    private Integer SNR3;
    private Integer SNR4;

    public DataPointModel(String sensor_id,
                          String sensor_mac,
                          double location_lon,
                          double location_lat,
                          String timestamp,
                          String datestamp,
                          double altitude,
                          double velocity,
                          boolean GPSerror,
                          boolean IMUerror,
                          boolean rightdirection,
                          double course,
                          double nsats,
                          int snr1,
                          int snr2,
                          int snr3,
                          int snr4) {
        this.sensor_id = sensor_id;
        this.sensor_mac = sensor_mac;
        this.location_lon = location_lon;
        this.location_lat = location_lat;
        this.timestamp = timestamp;
        this.datestamp = datestamp;
        this.altitude = altitude;
        this.velocity = velocity;
        this.GPSerror = GPSerror;
        this.IMUerror = IMUerror;
        this.rightdirection = rightdirection;
        this.course = course;
        this.nsats = nsats;
        this.SNR1 = snr1;
        this.SNR2 = snr2;
        this.SNR3 = snr3;
        this.SNR4 = snr4;
    }

    /*
    Alternate construction until I can patch in new dataset
     */
    public DataPointModel(String sensor_id,
                          String sensor_mac,
                          double location_lon,
                          double location_lat,
                          String timestamp) {
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

    public String getDatestamp() {
        return datestamp;
    }

    public double getAltitude() {
        //m sealevel
        return altitude;
    }

    public double getVelocity() {
        //m/s if you want in km/h *3.6
        return velocity;
    }

    public boolean getGPSerror() {
        //returns if GPS is not working
        return GPSerror;
    }

    public boolean getIMUerror() {
        //returns if the IMU (inertial measurement unit)
        // isn't working.
        return IMUerror;
    }

    public boolean getRightDirection() {
        //returns if the assembly is the right way up
        //Useful to tell if the solar panel won't work.
        return rightdirection;
    }

    public double getCourse() {
        //returns course heading from true course made good
        //RHS = 0 degrees
        //use this to determine direction something's heading.
        return course;
    }

    //don't worry about implementing anything from here
    public double getNumbersats() {
        //returns number of satelites in view
        //If it's higher than 256 i'll be shocked.
        return nsats;
    }

    public double getSNR1() {
        //shows where you're getting bad signal
        //returns dbm, if it's not higher than -80 then you've got problems
        return SNR1;
    }

    public double getSNR2() {
        //shows where you're getting bad signal
        //returns dbm, if it's not higher than -80 then you've got problems
        return SNR2;
    }

    public double getSNR3() {
        //shows where you're getting bad signal
        //returns dbm, if it's not higher than -80 then you've got problems
        return SNR3;
    }

    public double getSNR4() {
        //shows where you're getting bad signal
        //returns dbm, if it's not higher than -80 then you've got problems
        return SNR4;
    }

}
