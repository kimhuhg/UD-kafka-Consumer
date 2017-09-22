package com.union.bean;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by kejw on 2017/8/23.
 */
public class GPSMissInfo implements Serializable {
    private static final long serialVersionUID = 8046594233744971284L;
    private String plate       ;
    private String vehicleColor;
    private String vehicleType ;
    private String preTime     ;
    private String nextTime    ;
    private String preLon      ;
    private String preLat      ;
    private String nextLon     ;
    private String nextLat     ;
    private String preLoc      ;
    private String nextLoc     ;
    private String distance    ;
    private String duration    ;
    public GPSMissInfo(JSONObject jsonObj) {
        this.plate        = jsonObj.getString("plate");
        this.vehicleColor = jsonObj.getString("vehicleColor");
        this.vehicleType  = jsonObj.getString("vehicleType");
        this.preTime      = jsonObj.getString("preTime");
        this.nextTime     = jsonObj.getString("nextTime");
        this.preLon       = jsonObj.getString("preLon");
        this.preLat       = jsonObj.getString("preLat");
        this.nextLon      = jsonObj.getString("nextLon");
        this.nextLat      = jsonObj.getString("nextLat");
        this.preLoc       = jsonObj.getString("preLoc");
        this.nextLoc      = jsonObj.getString("nextLoc");
        this.distance     = jsonObj.getString("distance");
        this.duration     = jsonObj.getString("duration");
    }
    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getPreTime() {
        return preTime;
    }

    public void setPreTime(String preTime) {
        this.preTime = preTime;
    }

    public String getNextTime() {
        return nextTime;
    }

    public void setNextTime(String nextTime) {
        this.nextTime = nextTime;
    }

    public String getPreLon() {
        return preLon;
    }

    public void setPreLon(String preLon) {
        this.preLon = preLon;
    }

    public String getPreLat() {
        return preLat;
    }

    public void setPreLat(String preLat) {
        this.preLat = preLat;
    }

    public String getNextLon() {
        return nextLon;
    }

    public void setNextLon(String nextLon) {
        this.nextLon = nextLon;
    }

    public String getNextLat() {
        return nextLat;
    }

    public void setNextLat(String nextLat) {
        this.nextLat = nextLat;
    }

    public String getPreLoc() {
        return preLoc;
    }

    public void setPreLoc(String preLoc) {
        this.preLoc = preLoc;
    }

    public String getNextLoc() {
        return nextLoc;
    }

    public void setNextLoc(String nextLoc) {
        this.nextLoc = nextLoc;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
