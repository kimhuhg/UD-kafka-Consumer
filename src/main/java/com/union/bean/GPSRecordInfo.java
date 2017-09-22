package com.union.bean;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by kejw on 2017/8/21.
 */
public class GPSRecordInfo implements Serializable {
    private static final long serialVersionUID = 8059594233744971284L;
    private String plate;
    private String gpsTime;
    private String lon;
    private String lat;

    public GPSRecordInfo(JSONObject jsonObj) {
        this.plate = jsonObj.getString("plate");
        this.gpsTime = jsonObj.getString("gpsTime");
        this.lon = jsonObj.getString("lon");
        this.lat = jsonObj.getString("lat");
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

}
