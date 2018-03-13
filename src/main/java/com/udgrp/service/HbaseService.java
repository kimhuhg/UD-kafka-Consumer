package com.udgrp.service;

import com.udgrp.entity.ExListData;
import com.udgrp.entity.GPSMiss;
import com.udgrp.entity.GPSTrail;
import com.udgrp.constant.Constants;
import org.apache.hadoop.hbase.client.Put;
import org.springframework.stereotype.Component;

/**
 * @author kejw
 * @version V1.0
 * @Project ud-kafka-consumer
 * @Description: TODO
 * @date 2017/9/17
 */
@Component
public class HbaseService {

    public Put getGPSPut(GPSTrail gpsTrail, String rowkey) {
        Put put = new Put(rowkey.getBytes());
        put.addColumn(Constants.HB_GPS_FN.getBytes(), "plate".getBytes(), gpsTrail.getPlate().getBytes());
        put.addColumn(Constants.HB_GPS_FN.getBytes(), "gpstime".getBytes(), gpsTrail.getGpsTime().getBytes());
        put.addColumn(Constants.HB_GPS_FN.getBytes(), "lon".getBytes(), gpsTrail.getLon().getBytes());
        put.addColumn(Constants.HB_GPS_FN.getBytes(), "lat".getBytes(), gpsTrail.getLat().getBytes());
        return put;
    }

    public Put getSHWPut(ExListData exListData, String rowkey) {
        Put put = new Put(rowkey.getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "plate".getBytes(), exListData.getPlate().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "vehicleColor".getBytes(), exListData.getVehicleColor().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "vehicleType".getBytes(), exListData.getVehicleType().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "driveScope".getBytes(), exListData.getDriveScope().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "enTime".getBytes(), exListData.getEnTime().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "exTime".getBytes(), exListData.getExTime().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "enloadNetName".getBytes(), exListData.getEnloadNetName().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "enLoadName".getBytes(), exListData.getEnLoadName().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "enStationName".getBytes(), exListData.getEnStationName().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "exloadNetName".getBytes(), exListData.getExloadNetName().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "exLoadName".getBytes(), exListData.getExLoadName().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "exStationName".getBytes(), exListData.getExStationName().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "enlon".getBytes(), exListData.getEnlon().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "enlat".getBytes(), exListData.getEnlat().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "exlon".getBytes(), exListData.getExlon().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "exlat".getBytes(), exListData.getExlat().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "enCity".getBytes(), exListData.getEnCity().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "exCity".getBytes(), exListData.getExCity().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "midstations".getBytes(), exListData.getMidstations().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "minutes".getBytes(), exListData.getMinutes().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "km".getBytes(), exListData.getKm().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "preSpeed".getBytes(), exListData.getPreSpeed().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "isFatigueDrive".getBytes(), exListData.getIsFatigueDrive().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "isSameCity".getBytes(), exListData.getIsSameCity().getBytes());

        return put;
    }

    public Put getGPSMISSPut(GPSMiss gpsMiss, String rowkey) {
        Put put = new Put(rowkey.getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "plate".getBytes(), gpsMiss.getPlate().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "vehicleColor".getBytes(), gpsMiss.getVehicleColor().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "vehicleType".getBytes(), gpsMiss.getVehicleType().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "preTime".getBytes(), gpsMiss.getPreTime().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "nextTime".getBytes(), gpsMiss.getNextTime().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "preLon".getBytes(), gpsMiss.getPreLon().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "preLat".getBytes(), gpsMiss.getPreLat().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "nextLon".getBytes(), gpsMiss.getNextLon().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "nextLat".getBytes(), gpsMiss.getNextLat().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "preLoc".getBytes(), gpsMiss.getPreLoc().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "nextLoc".getBytes(), gpsMiss.getNextLoc().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "distance".getBytes(), gpsMiss.getDistance().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "duration".getBytes(), gpsMiss.getDuration().getBytes());
        return put;
    }

}
