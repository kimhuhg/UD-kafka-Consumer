package com.union.constant;

import org.apache.hadoop.hbase.client.Put;
import org.elasticsearch.action.index.IndexRequest;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kejw on 2017/9/27.
 */
public class Global {
    public static final ConcurrentHashMap<String, ArrayList<Put>> putMap = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, ArrayList<IndexRequest>> indexRequestMap = new ConcurrentHashMap<>();

    public static final ConcurrentHashMap<String, Integer> hbaseCountMap = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, Integer> esCountMap = new ConcurrentHashMap<>();

    //public static final String topics[] = {Constants.GPS_TOPIC, Constants.SHW_TOPIC, Constants.GPS_MISS_TOPIC};
    public static final String topics[] = {Constants.GPS_TOPIC};



    public static volatile ConcurrentHashMap<String, Integer> times = new ConcurrentHashMap<>();

    public static volatile ConcurrentHashMap<String, Long> lastEsTotal = new ConcurrentHashMap<>();

}
