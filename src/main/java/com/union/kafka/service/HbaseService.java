package com.union.kafka.service;

import com.union.bean.ExListCarInfo;
import com.union.bean.GPSMissInfo;
import com.union.bean.GPSRecordInfo;
import com.union.constant.Constants;
import org.apache.hadoop.hbase.client.*;
import org.springframework.stereotype.Component;

/**
 * Created by kejw on 2017/9/17.
 */
@Component
public class HbaseService {

    public Put getGPSPut(GPSRecordInfo gpsRecordInfo, String rowkey) {
        Put put = new Put(rowkey.getBytes());
        put.addColumn(Constants.HB_GPS_FN.getBytes(), "plate".getBytes(), gpsRecordInfo.getPlate().getBytes());
        put.addColumn(Constants.HB_GPS_FN.getBytes(), "gpsTime".getBytes(), gpsRecordInfo.getGpsTime().getBytes());
        put.addColumn(Constants.HB_GPS_FN.getBytes(), "lon".getBytes(), gpsRecordInfo.getLon().getBytes());
        put.addColumn(Constants.HB_GPS_FN.getBytes(), "lat".getBytes(), gpsRecordInfo.getLat().getBytes());
        return put;
    }

    public Put getSHWPut(ExListCarInfo exListCarInfo, String rowkey) {
        Put put = new Put(rowkey.getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "plate".getBytes(), exListCarInfo.getPlate().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "vehicleColor".getBytes(), exListCarInfo.getVehicleColor().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "vehicleType".getBytes(), exListCarInfo.getVehicleType().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "driveScope".getBytes(), exListCarInfo.getDriveScope().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "enTime".getBytes(), exListCarInfo.getEnTime().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "exTime".getBytes(), exListCarInfo.getExTime().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "enloadNetName".getBytes(), exListCarInfo.getEnloadNetName().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "enLoadName".getBytes(), exListCarInfo.getEnLoadName().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "enStationName".getBytes(), exListCarInfo.getEnStationName().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "exloadNetName".getBytes(), exListCarInfo.getExloadNetName().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "exLoadName".getBytes(), exListCarInfo.getExLoadName().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "exStationName".getBytes(), exListCarInfo.getExStationName().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "enlon".getBytes(), exListCarInfo.getEnlon().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "enlat".getBytes(), exListCarInfo.getEnlat().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "exlon".getBytes(), exListCarInfo.getExlon().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "exlat".getBytes(), exListCarInfo.getExlat().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "enCity".getBytes(), exListCarInfo.getEnCity().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "exCity".getBytes(), exListCarInfo.getExCity().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "midstations".getBytes(), exListCarInfo.getMidstations().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "minutes".getBytes(), exListCarInfo.getMinutes().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "km".getBytes(), exListCarInfo.getKm().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "preSpeed".getBytes(), exListCarInfo.getPreSpeed().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "isFatigueDrive".getBytes(), exListCarInfo.getIsFatigueDrive().getBytes());
        put.addColumn(Constants.HB_SHW_FN.getBytes(), "isSameCity".getBytes(), exListCarInfo.getIsSameCity().getBytes());

        return put;
    }

    public Put getGPSMISSPut(GPSMissInfo gpsMissInfo, String rowkey) {
        Put put = new Put(rowkey.getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "plate".getBytes(), gpsMissInfo.getPlate().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "vehicleColor".getBytes(), gpsMissInfo.getVehicleColor().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "vehicleType".getBytes(), gpsMissInfo.getVehicleType().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "preTime".getBytes(), gpsMissInfo.getPreTime().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "nextTime".getBytes(), gpsMissInfo.getNextTime().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "preLon".getBytes(), gpsMissInfo.getPreLon().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "preLat".getBytes(), gpsMissInfo.getPreLat().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "nextLon".getBytes(), gpsMissInfo.getNextLon().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "nextLat".getBytes(), gpsMissInfo.getNextLat().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "preLoc".getBytes(), gpsMissInfo.getPreLoc().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "nextLoc".getBytes(), gpsMissInfo.getNextLoc().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "distance".getBytes(), gpsMissInfo.getDistance().getBytes());
        put.addColumn(Constants.HB_GPS_MISS_FN.getBytes(), "duration".getBytes(), gpsMissInfo.getDuration().getBytes());
        return put;
    }

}
