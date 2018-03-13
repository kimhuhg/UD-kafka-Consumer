package com.udgrp.constant;

import com.udgrp.entity.BaseEntity;
import org.apache.hadoop.hbase.client.Put;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Project ud-kafka-kafka
 * @Description: TODO
 * @author kejw
 * @date 2017/9/27
 * @version V1.0
 */
public class Global {
    /**
     * 缓存待保存到ES和hbase的信息
     **/
    public static volatile Map<String, ArrayList<Put>> putMap = new HashMap<>();
    public static volatile  Map<String, ArrayList<BaseEntity>> entityMap = new HashMap<>();
    /**
     * 缓每个topic上一次定时剩下的记录数
     **/
    public static final Map<String, Integer> HBASE_LAST_COUNT = new HashMap<>();
    public static final Map<String, Integer> MYSQL_LAST_COUNT = new HashMap<>();
    /**
     * 所有的topic
     **/
    public static final String[] CONSUMER_TO_MYSQL = {Constants.KAFKA_SHW_TOPIC, Constants.KAFKA_GPS_MISS_TOPIC};
    public static final String[] CONSUMER_TO_HBASE = {Constants.KAFKA_GPS_TOPIC};
    /**
     * 一个标记，当time>=3，即认为该topic数据已消费完毕
     **/
    public static volatile Map<String, Integer> TIMES = new HashMap<>();
}
