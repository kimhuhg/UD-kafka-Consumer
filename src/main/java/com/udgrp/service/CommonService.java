package com.udgrp.service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.udgrp.common.PoolsImpl;
import com.udgrp.constant.Constants;
import com.udgrp.constant.Global;
import com.udgrp.entity.BaseEntity;
import com.udgrp.entity.ExListData;
import com.udgrp.entity.GPSMiss;
import com.udgrp.entity.GPSTrail;
import com.udgrp.mapper.impl.ExListDataMapperImpl;
import com.udgrp.mapper.impl.GPSMissMapperImpl;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author kejw
 * @version V1.0
 * @Project ud-kafka-consumer
 * @Description: TODO
 * @date 2017/9/21
 */
@Component
public class CommonService {
    private static final Lock lock = new ReentrantLock();
    public static final Logger logger = LoggerFactory.getLogger(CommonService.class);
    @Autowired
    private HbaseService hbaseService;
    @Autowired
    private ElasticSearchService esService;
    @Autowired
    private ExListDataMapperImpl exListDataMapper;
    @Autowired
    private GPSMissMapperImpl gpsMissMapper;
    @Autowired
    private PoolsImpl pool;

    public void consumerGPSData(ConsumerRecord<String, String> msg) {
        String topic = msg.topic();
        try {
            JSONObject jsonObj = JSONObject.parseObject(msg.value());
            GPSTrail gpsTrail = new GPSTrail(jsonObj);
            lock.lock();
            Global.putMap.get(topic).add(hbaseService.getGPSPut(gpsTrail, msg.key()));
            if (Global.putMap.get(topic).size() >= Constants.HBASE_BATCH_COUNT) {
                ArrayList<Put> putList = (ArrayList<Put>) Global.putMap.get(topic).clone();
                Global.putMap.get(topic).clear();
                asynToHbase(putList, Constants.getHtable(topic));
            }
        } catch (JSONException e) {
            logger.error("数据格式错误，请检查数据保存的JSON格式！", e);
        } catch (Exception e) {
            logger.error("处理数据异常，请检查你的代码！", e);
        } finally {
            lock.unlock();
        }
    }

    public void consumerSHWData(ConsumerRecord<String, String> msg) {
        String topic = msg.topic();
        try {
            JSONObject jsonObj = JSONObject.parseObject(msg.value());
            ExListData exListData = new ExListData(jsonObj);
            exListData.setId(msg.key());
            lock.lock();
            Global.entityMap.get(topic).add(exListData);
            if (Global.entityMap.get(topic).size() >= Constants.MYSQL_BATCH_COUNT) {
                ArrayList<ExListData> exListDatas = (ArrayList<ExListData>) Global.entityMap.get(topic).clone();
                Global.entityMap.get(topic).clear();
                exListDataMapper.asynBatchInsert(exListDatas);
            }
        } catch (JSONException e) {
            logger.error("数据格式错误，请检查数据保存的JSON格式！", e);
        } catch (Exception e) {
            logger.error("处理数据异常，请检查你的代码！", e);
        } finally {
            lock.unlock();
        }
    }

    public void consumerGPSMISSData(ConsumerRecord<String, String> msg) {
        String topic = msg.topic();
        try {
            JSONObject jsonObj = JSONObject.parseObject(msg.value());
            GPSMiss gpsMiss = new GPSMiss(jsonObj);
            gpsMiss.setId(msg.key());
            lock.lock();
            Global.entityMap.get(topic).add(gpsMiss);
            if (Global.entityMap.get(topic).size() >= Constants.MYSQL_BATCH_COUNT) {
                ArrayList<GPSMiss> gpsMisss = (ArrayList<GPSMiss>) Global.entityMap.get(topic).clone();
                Global.entityMap.get(topic).clear();
                gpsMissMapper.asynBatchInsert(gpsMisss);
            }
        } catch (JSONException e) {
            logger.error("数据格式错误，请检查数据保存的JSON格式！", e);
        } catch (Exception e) {
            logger.error("处理数据异常，请检查你的代码！", e);
        } finally {
            lock.unlock();
        }
    }

    public void saveMysql(ArrayList<BaseEntity> entitys) {
        for (BaseEntity entity : entitys) {
            if (entity instanceof ExListData) {
                exListDataMapper.saveIfNoExist((ExListData) entity);
            } else if (entity instanceof GPSMiss) {
                gpsMissMapper.saveIfNoExist((GPSMiss) entity);
            }
        }
    }

    public void saveHbase(String hTable, ArrayList<Put> puts) {
        asynToHbase(puts, hTable);
    }

    /**
     * 异步保存到hbase
     *
     * @param hbaseData
     */
    private void asynToHbase(ArrayList<Put> hbaseData, String hTable) {
        Connection hbaseConn = pool.getHbaseConnection();
        final BufferedMutator.ExceptionListener listener = new BufferedMutator.ExceptionListener() {
            @Override
            public void onException(RetriesExhaustedWithDetailsException e, BufferedMutator mutator) {
                for (int i = 0; i < e.getNumExceptions(); i++) {
                    logger.error("Failed to sent put " + e.getRow(i) + "." + e.getMessage());
                }
            }
        };
        BufferedMutatorParams params = new BufferedMutatorParams(TableName.valueOf(hTable)).listener(listener);
        try {
            final BufferedMutator mutator = hbaseConn.getBufferedMutator(params);
            Executors.newFixedThreadPool(30).execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        mutator.mutate(hbaseData);
                        mutator.close();
                        hbaseData.clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        pool.returnHbaseConnection(hbaseConn);
                    }
                }
            });
        } catch (IOException e) {
            logger.error("exception while creating/destroying Connection or BufferedMutator", e.getMessage());
        }
    }




    /**
     * 异步保存到es
     *
     * @param esData
     */
    private void asynToES(ArrayList<IndexRequest> esData) {
        TransportClient esConn = pool.getEsConnection();
        BulkRequestBuilder esclientBuk = esService.addEBuk(esConn.prepareBulk(), esData);
        final ActionListener esListener = new ActionListener<BulkResponse>() {
            @Override
            public void onFailure(Exception e) {
                logger.error("ES failure ==>" + e.getMessage());
            }

            @Override
            public void onResponse(BulkResponse response) {
                //logger.info(response.toString());
            }
        };
        Executors.newFixedThreadPool(30).execute(new Runnable() {
            @Override
            public void run() {
                esclientBuk.execute(esListener);
                esData.clear();
                pool.returnEsConnection(esConn);

            }
        });
    }

    public long getEsTotal(String topic) {
        Assert.hasLength(topic, "topic must be specified");
        TransportClient esConn = pool.getEsConnection();
        long count = 0L;
        try {
            SearchResponse response = esConn.prepareSearch()
                    .setIndices(Constants.getIndex(topic))
                    .setTypes(Constants.getType(topic))
                    .setSize(0)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .get();
            count = response.getHits().totalHits;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询ES记录总数异常：" + e.getMessage());
        } finally {
            pool.returnEsConnection(esConn);
        }
        return count;
    }

    @Test
    public void test() {
        System.out.println(getEsTotal("kafka_jtt_gps_test"));
    }
}
