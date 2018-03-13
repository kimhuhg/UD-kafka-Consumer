package com.udgrp.bean;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * @Project ud-kafka-kafka
 * @Description: TODO
 * @author kejw
 * @date 2017/10/12
 * @version V1.0
 */
public class GPSRecordInfo2 implements Serializable {
    private static final long serialVersionUID = 8059594233742441284L;
    private String vehicleNo;
    private String vehicleColor;
    private String gpsTime;
    private String lon;
    private String lat;
    private String vec1;
    private String vec3;
    private String direction;
    private String altitude;
    private String state;
    private String alarm;

    public GPSRecordInfo2(JSONObject jsonObj) {
        this.vehicleNo = jsonObj.getString("vehicleNo");
        this.vehicleColor = jsonObj.getString("vehicleColor");
        this.gpsTime = jsonObj.getString("gpsTime");
        this.lon = jsonObj.getString("lon");
        this.lat = jsonObj.getString("lat");
        this.vec1 = jsonObj.getString("vec1");
        this.vec3 = jsonObj.getString("vec3");
        this.direction = jsonObj.getString("direction");
        this.altitude = jsonObj.getString("altitude");
        this.state = jsonObj.getString("state");
        this.alarm = jsonObj.getString("alarm");
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(String gpsTime) {
        this.gpsTime = gpsTime;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getVec1() {
        return vec1;
    }

    public void setVec1(String vec1) {
        this.vec1 = vec1;
    }

    public String getVec3() {
        return vec3;
    }

    public void setVec3(String vec3) {
        this.vec3 = vec3;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }
}
