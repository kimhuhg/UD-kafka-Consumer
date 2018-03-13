package com.udgrp.service;

import com.udgrp.entity.ExListData;
import com.udgrp.entity.GPSMiss;
import com.udgrp.entity.GPSTrail;
import com.udgrp.constant.Constants;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Project ud-kafka-consumer
 * @Description: TODO
 * @author kejw
 * @date 2017/9/17
 * @version V1.0
 */
@Component
public class ElasticSearchService {
    public IndexRequest getGPSIndexReques(GPSTrail gpsTrail, String rowkey) {
        IndexRequest indexRequest = new IndexRequest(Constants.ES_GPS_INDEX, Constants.ES_GPS_TYPE,rowkey);
        Map<String, Object> data = new HashMap<>();
        data.put("plate", gpsTrail.getPlate());
        data.put("date", gpsTrail.getGpsTime());
        data.put("rowKey", rowkey);
        indexRequest.source(data);
        return indexRequest;
    }

    public IndexRequest getSHWIndexReques(ExListData exListData, String rowkey) {
        IndexRequest indexRequest = new IndexRequest(Constants.ES_SHW_INDEX, Constants.ES_SHW_TYPY, rowkey);
        Map<String, Object> data = new HashMap<>();
        data.put("vehicleColor", exListData.getVehicleColor());
        data.put("vehicleType", exListData.getVehicleType());
        data.put("driveScope", exListData.getDriveScope());
        data.put("rowKey", rowkey);
        data.put("plate", exListData.getPlate());
        data.put("enCity", exListData.getEnCity());
        data.put("exCity", exListData.getExCity());
        data.put("isSameCity", exListData.getIsSameCity());
        data.put("date", exListData.getExTime().substring(0, 10));
        data.put("isFatigueDrive", exListData.getIsFatigueDrive());
        data.put("pd", exListData.getPlate() + "_" + exListData.getExTime().substring(0, 10).replaceAll("-", ""));
        indexRequest.source(data);
        return indexRequest;
    }
    public IndexRequest getGPSMISSIndexReques(GPSMiss gpsMiss, String rowkey) {
        IndexRequest indexRequest = new IndexRequest(Constants.ES_GPS_MISS_INDEX, Constants.ES_GPS_MISS_TYPY, rowkey);
        Map<String, Object> data = new HashMap<>();
        data.put("rowKey", rowkey);
        data.put("plate", gpsMiss.getPlate());
        data.put("preTime", gpsMiss.getPreTime());
        data.put("nextTime", gpsMiss.getNextTime());
        indexRequest.source(data);
        return indexRequest;
    }

    public BulkRequestBuilder addEBuk(BulkRequestBuilder esclientBuk, List<IndexRequest> esData) {
        for (IndexRequest indexRequest : esData) {
            esclientBuk.add(indexRequest);
        }
        return esclientBuk;
    }
}
