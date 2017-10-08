package com.union.kafka.service;

import com.union.bean.ExListCarInfo;
import com.union.bean.GPSMissInfo;
import com.union.bean.GPSRecordInfo;
import com.union.constant.Constants;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kejw on 2017/9/17.
 */
@Component
public class ElasticSearchService {
    public IndexRequest getGPSIndexReques(GPSRecordInfo gpsRecordInfo, String rowkey) {
        IndexRequest indexRequest = new IndexRequest(Constants.ES_GPS_INDEX, Constants.ES_GPS_TYPE,rowkey);
        Map<String, Object> data = new HashMap<>();
        data.put("plate", gpsRecordInfo.getPlate());
        data.put("date", gpsRecordInfo.getGpsTime());
        data.put("rowKey", rowkey);
        indexRequest.source(data);
        return indexRequest;
    }

    public IndexRequest getSHWIndexReques(ExListCarInfo exListCarInfo, String rowkey) {
        IndexRequest indexRequest = new IndexRequest(Constants.ES_SHW_INDEX, Constants.ES_GPS_TYPE, rowkey);
        Map<String, Object> data = new HashMap<>();
        data.put("vehicleColor", exListCarInfo.getVehicleColor());
        data.put("vehicleType", exListCarInfo.getVehicleType());
        data.put("driveScope", exListCarInfo.getDriveScope());
        data.put("rowKey", rowkey);
        data.put("plate", exListCarInfo.getPlate());
        data.put("enCity", exListCarInfo.getEnCity());
        data.put("exCity", exListCarInfo.getExCity());
        data.put("isSameCity", exListCarInfo.getIsSameCity());
        data.put("date", exListCarInfo.getExTime().substring(0, 10));
        data.put("isFatigueDrive", exListCarInfo.getIsFatigueDrive());
        data.put("pd", exListCarInfo.getPlate() + "_" + exListCarInfo.getExTime().substring(0, 10).replaceAll("-", ""));
        indexRequest.source(data);
        return indexRequest;
    }
    public IndexRequest getGPSMISSIndexReques(GPSMissInfo gpsMissInfo, String rowkey) {
        IndexRequest indexRequest = new IndexRequest(Constants.ES_GPS_MISS_INDEX, Constants.ES_GPS_MISS_TYPY, rowkey);
        Map<String, Object> data = new HashMap<>();
        data.put("rowKey", rowkey);
        data.put("plate", gpsMissInfo.getPlate());
        data.put("preTime", gpsMissInfo.getPreTime());
        data.put("nextTime", gpsMissInfo.getNextTime());
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
