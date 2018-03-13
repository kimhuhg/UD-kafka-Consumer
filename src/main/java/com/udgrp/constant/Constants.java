package com.udgrp.constant;

import org.springframework.util.Assert;

import java.io.Serializable;

import static com.udgrp.common.ConfLoader.conf;

/**
 * @author kejw
 * @version V1.0
 * @Project ud-kafka-kafka
 * @Description: TODO
 * @date 2017/6/16
 */
public class Constants implements Serializable {

    /**
     * ES常量
     */
    public static final String ES_SHW_INDEX = conf.getString("ES_SHW_INDEX");
    public static final String ES_GPS_INDEX = conf.getString("ES_GPS_INDEX");
    public static final String ES_GPS_MISS_INDEX = conf.getString("ES_GPS_MISS_INDEX");

    public static final String ES_SHW_TYPY = conf.getString("ES_SHW_TYPY");
    public static final String ES_GPS_TYPE = conf.getString("ES_GPS_TYPE");
    public static final String ES_GPS_MISS_TYPY = conf.getString("ES_GPS_MISS_TYPY");

    /**
     * HABSE常量
     */
    public static final String HB_GPS_TN = conf.getString("HB_GPS_TN");
    public static final String HB_SHW_TN = conf.getString("HB_SHW_TN");
    public static final String HB_GPS_MISS_TN = conf.getString("HB_GPS_MISS_TN");

    public static final String HB_SHW_FN = conf.getString("HB_SHW_FN");
    public static final String HB_GPS_FN = conf.getString("HB_GPS_FN");
    public static final String HB_GPS_MISS_FN = conf.getString("HB_GPS_MISS_FN");

    /**
     * KAFKA常量
     */
    public static final String KAFKA_GPS_TOPIC = conf.getString("KAFKA_GPS_TOPIC");
    public static final String KAFKA_SHW_TOPIC = conf.getString("KAFKA_SHW_TOPIC");
    public static final String KAFKA_GPS_MISS_TOPIC = conf.getString("KAFKA_GPS_MISS_TOPIC");

    public static final int MYSQL_BATCH_COUNT = conf.getInt("MYSQL_BATCH_COUNT");
    public static final int HBASE_BATCH_COUNT = conf.getInt("HBASE_BATCH_COUNT");

    public static String getHtable(String topic) {
        Assert.hasLength(topic, "topic must be specified");
        if (topic.equals(KAFKA_GPS_TOPIC)) {
            return HB_GPS_TN;
        } else if (topic.equals(KAFKA_SHW_TOPIC)) {
            return HB_SHW_TN;
        } else if (topic.equals(KAFKA_GPS_MISS_TOPIC)) {
            return HB_GPS_MISS_TN;
        } else {
            return "";
        }
    }

    public static String getIndex(String topic) {
        Assert.hasLength(topic, "topic must be specified");
        if (topic.equals(KAFKA_GPS_TOPIC)) {
            return ES_GPS_INDEX;
        } else if (topic.equals(KAFKA_SHW_TOPIC)) {
            return ES_SHW_INDEX;
        } else if (topic.equals(KAFKA_GPS_MISS_TOPIC)) {
            return ES_GPS_MISS_INDEX;
        } else {
            return "";
        }
    }

    public static String getType(String topic) {
        Assert.hasLength(topic, "topic must be specified");
        if (topic.equals(KAFKA_GPS_TOPIC)) {
            return ES_GPS_TYPE;
        } else if (topic.equals(KAFKA_SHW_TOPIC)) {
            return ES_SHW_TYPY;
        } else if (topic.equals(KAFKA_GPS_MISS_TOPIC)) {
            return ES_GPS_MISS_TYPY;
        } else {
            return "";
        }
    }
}
