package com.nathanglover.digitribe;

public class DataPointModel {

    private String sensor_id;
    private String sensor_mac;
    private double location_lon;
    private double location_lat;
    private String timestamp;
    private String datestamp;
    private Double altitude;
    private Double velocity;
    private Boolean GPSerror;
    private Boolean IMUerror;
    private Boolean rightdirection;
    private Double course;
    private Double nsats;
    private Integer SNR1;
    private Integer SNR2;
    private Integer SNR3;
    private Integer SNR4;
  

    public DataPointModel(String sensor_id, String sensor_mac, double location_lon, double location_lat, String timestamp,
                         String datestamp, Double altitude, Double velocity, Boolean GPSerror, Boolean IMUerror, Boolean rightdirection
                         Double course, Double nsats, Integer SNR1, Integer SNR2, Integer SNR3, Integer SNR4) {
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
        this.nsats = Numbersats;
        this.SNR1 = SNR1;
        this.SNR2 = SNR2;
        this.SNR3 = SNR3;
        this.SNR4 = SNR4;
        
        
  
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
    
        public boolean getGPSerror(){
            //returns if GPS is not working
            return GPSerror;
    }
    
        public boolean getIMUerror(){
            //returns if the IMU (inertial measurement unit)
            // isn't working.
            return IMUerror;
    }
        public boolean getRightdirection(){
            //returns if the assembly is the right way up
            //Useful to tell if the solar panel won't work.
            return RightDirection;
    }
    
        public double getCourse(){
            //returns course heading from true course made good
            //RHS = 0 degrees
            //use this to determine direction something's heading.
            return course;
    }
    //don't worry about implementing anything from here
        public int getNumbersats(){
            //returns number of satelites in view
            //If it's higher than 256 i'll be shocked.
            return Numbersats;
    }

        public double getSNR1(){
            //shows where you're getting bad signal
            //returns dbm, if it's not higher than -80 then you've got problems
            return SNR1;
    }
        public double getSNR2(){
            //shows where you're getting bad signal
            //returns dbm, if it's not higher than -80 then you've got problems
            return SNR2;
    }
        public double getSNR3(){
            //shows where you're getting bad signal
            //returns dbm, if it's not higher than -80 then you've got problems
            return SNR3;
    }
        public double getSNR4(){
            //shows where you're getting bad signal
            //returns dbm, if it's not higher than -80 then you've got problems
            return SNR4;
    }
    
}
