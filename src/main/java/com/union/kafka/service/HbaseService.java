package com.union.kafka.service;

import com.alibaba.fastjson.JSONObject;
import com.union.bean.ExListCarInfo;
import com.union.bean.GPSRecordInfo;
import com.union.common.base.InternalPools;
import com.union.common.constant.Constants;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;

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



}
