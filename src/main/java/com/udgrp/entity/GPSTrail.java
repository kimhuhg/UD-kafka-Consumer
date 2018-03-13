package com.udgrp.entity;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * @author kejw
 * @version V1.0
 * @Project ud-kafka-kafka
 * @Description: TODO
 * @date 2017/8/21
 */
public class GPSTrail implements Serializable {
    private static final long serialVersionUID = 8059594233744971284L;
    private String plate;
    private String gpsTime;
    private String lon;
    private String lat;
    private Double distances;

    public GPSTrail(JSONObject jsonObj) {
        this.plate = jsonObj.getString("plate");
        this.gpsTime = jsonObj.getString("gpsTime");
        this.lon = jsonObj.getString("lon");
        this.lat = jsonObj.getString("lat");
        this.distances = jsonObj.getDouble("distances");
    }

    public Double getDistances() {
        return distances;
    }

    public void setDistances(Double distances) {
        this.distances = distances;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
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
/*
    private String plate;
    private String vehicleColor;
    private String vehicleType;
    private String GPSTime;
    private String lon;
    private String lat;
    private String vec1;
    private String vec2;
    private String vec3;
    private String direction;
    private String altitude;
    private String state;
    private String alarm;

    public GPSTrail(JSONObject jsonObj) {
        this.plate = jsonObj.getString("plate");
        this.vehicleColor = jsonObj.getString("vehicleColor");
        this.vehicleType = jsonObj.getString("vehicleType");
        this.GPSTime = jsonObj.getString("gpstime");
        this.lon = jsonObj.getString("lon");
        this.lat = jsonObj.getString("lat");
        this.vec1 = jsonObj.getString("vec1");
        this.vec2 = jsonObj.getString("vec2");
        this.vec3 = jsonObj.getString("vec3");
        this.direction = jsonObj.getString("direction");
        this.altitude = jsonObj.getString("altitude");
        this.state = jsonObj.getString("state");
        this.alarm = jsonObj.getString("alarm");
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

    public String getGPSTime() {
        return GPSTime;
    }

    public void setGPSTime(String GPSTime) {
        this.GPSTime = GPSTime;
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

    public String getVec2() {
        return vec2;
    }

    public void setVec2(String vec2) {
        this.vec2 = vec2;
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
    }*/

}
