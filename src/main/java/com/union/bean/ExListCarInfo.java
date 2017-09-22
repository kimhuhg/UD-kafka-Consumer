package com.union.bean;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by kejw on 2017/8/22.
 */
public class ExListCarInfo implements Serializable {
    private static final long serialVersionUID = 8059594223144971284L;
    private String plate;
    private String vehicleColor;
    private String vehicleType;
    private String driveScope;
    private String enTime;
    private String exTime;
    private String enloadNetName;
    private String enLoadName;
    private String enStationName;
    private String exloadNetName;
    private String exLoadName;
    private String exStationName;
    private String enlon;
    private String enlat;
    private String exlon;
    private String exlat;
    private String enCity;
    private String exCity;
    private String midstations;
    private String minutes;
    private String km;
    private String preSpeed;
    private String isFatigueDrive;
    private String isSameCity;

    public ExListCarInfo(JSONObject jsonObj) {
        this.plate = jsonObj.getString("plate");
        this.vehicleColor = jsonObj.getString("vehicleColor");
        this.vehicleType = jsonObj.getString("vehicleType");
        this.driveScope = jsonObj.getString("driveScope");
        this.enTime = jsonObj.getString("enTime");
        this.exTime = jsonObj.getString("exTime");
        this.enloadNetName = jsonObj.getString("enloadNetName");
        this.enLoadName = jsonObj.getString("enLoadName");
        this.enStationName = jsonObj.getString("enStationName");
        this.exloadNetName = jsonObj.getString("exloadNetName");
        this.exLoadName = jsonObj.getString("exLoadName");
        this.exStationName = jsonObj.getString("exStationName");
        this.enlon = jsonObj.getString("enlon");
        this.enlat = jsonObj.getString("enlat");
        this.exlon = jsonObj.getString("exlon");
        this.exlat = jsonObj.getString("exlat");
        this.enCity = jsonObj.getString("enCity");
        this.exCity = jsonObj.getString("exCity");
        this.midstations = jsonObj.getString("midstations");
        this.minutes = jsonObj.getString("minutes");
        this.km = jsonObj.getString("km");
        this.preSpeed = jsonObj.getString("preSpeed");
        this.isFatigueDrive = jsonObj.getString("isFatigueDrive");
        this.isSameCity = jsonObj.getString("isSameCity");
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

    public String getDriveScope() {
        return driveScope;
    }

    public void setDriveScope(String driveScope) {
        this.driveScope = driveScope;
    }

    public String getEnTime() {
        return enTime;
    }

    public void setEnTime(String enTime) {
        this.enTime = enTime;
    }

    public String getExTime() {
        return exTime;
    }

    public void setExTime(String exTime) {
        this.exTime = exTime;
    }

    public String getEnloadNetName() {
        return enloadNetName;
    }

    public void setEnloadNetName(String enloadNetName) {
        this.enloadNetName = enloadNetName;
    }

    public String getEnLoadName() {
        return enLoadName;
    }

    public void setEnLoadName(String enLoadName) {
        this.enLoadName = enLoadName;
    }

    public String getEnStationName() {
        return enStationName;
    }

    public void setEnStationName(String enStationName) {
        this.enStationName = enStationName;
    }

    public String getExloadNetName() {
        return exloadNetName;
    }

    public void setExloadNetName(String exloadNetName) {
        this.exloadNetName = exloadNetName;
    }

    public String getExLoadName() {
        return exLoadName;
    }

    public void setExLoadName(String exLoadName) {
        this.exLoadName = exLoadName;
    }

    public String getExStationName() {
        return exStationName;
    }

    public void setExStationName(String exStationName) {
        this.exStationName = exStationName;
    }

    public String getEnlon() {
        return enlon;
    }

    public void setEnlon(String enlon) {
        this.enlon = enlon;
    }

    public String getEnlat() {
        return enlat;
    }

    public void setEnlat(String enlat) {
        this.enlat = enlat;
    }

    public String getExlon() {
        return exlon;
    }

    public void setExlon(String exlon) {
        this.exlon = exlon;
    }

    public String getExlat() {
        return exlat;
    }

    public void setExlat(String exlat) {
        this.exlat = exlat;
    }

    public String getEnCity() {
        return enCity;
    }

    public void setEnCity(String enCity) {
        this.enCity = enCity;
    }

    public String getExCity() {
        return exCity;
    }

    public void setExCity(String exCity) {
        this.exCity = exCity;
    }

    public String getMidstations() {
        return midstations;
    }

    public void setMidstations(String midstations) {
        this.midstations = midstations;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getPreSpeed() {
        return preSpeed;
    }

    public void setPreSpeed(String preSpeed) {
        this.preSpeed = preSpeed;
    }

    public String getIsFatigueDrive() {
        return isFatigueDrive;
    }

    public void setIsFatigueDrive(String isFatigueDrive) {
        this.isFatigueDrive = isFatigueDrive;
    }

    public String getIsSameCity() {
        return isSameCity;
    }

    public void setIsSameCity(String isSameCity) {
        this.isSameCity = isSameCity;
    }
}