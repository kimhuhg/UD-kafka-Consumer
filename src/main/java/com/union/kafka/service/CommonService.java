package com.union.kafka.service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.union.bean.ExListCarInfo;
import com.union.bean.GPSRecordInfo;
import com.union.common.base.InternalPools;
import com.union.common.constant.Constants;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by kejw on 2017/9/21.
 */
@Component
public class CommonService extends InternalPools {
    private static Logger logger = LoggerFactory.getLogger(CommonService.class);
    private static final int batchCount = 10000;

    @Autowired
    private HbaseService hbaseService;
    @Autowired
    private ElasticSearchService esService;

    private static final Lock lock = new ReentrantLock();

    public void prePareGPSData(ConsumerRecord<String, String> msg, ArrayList<Put> puts, ArrayList<IndexRequest> indexRequests) {
        try {
            JSONObject jsonObj = JSONObject.parseObject(msg.value());
            GPSRecordInfo gpsRecordInfo = new GPSRecordInfo(jsonObj);
            lock.lock();
            puts.add(hbaseService.getGPSPut(gpsRecordInfo, msg.key()));
            indexRequests.add(esService.getGPSIndexReques(gpsRecordInfo, msg.key()));
            judgeAsynBatch(puts, indexRequests, Constants.TABLE_GPS_NAME);
        } catch (JSONException e) {
            logger.error("数据格式错误，请检查数据保存的JSON格式！", e);
        } catch (Exception e) {
            logger.error("处理数据异常，请检查你的代码！", e);
        } finally {
            lock.unlock();
        }
    }

    public void prePareSHWData(ConsumerRecord<String, String> msg, ArrayList<Put> puts, ArrayList<IndexRequest> indexRequests) {
        try {
            JSONObject jsonObj = JSONObject.parseObject(msg.value());
            ExListCarInfo exListCarInfo = new ExListCarInfo(jsonObj);
            lock.lock();
            puts.add(hbaseService.getSHWPut(exListCarInfo, msg.key()));
            indexRequests.add(esService.getSHWIndexReques(exListCarInfo, msg.key()));
            judgeAsynBatch(puts, indexRequests, Constants.TABLE_SHW_NAME);
        } catch (JSONException e) {
            logger.error("数据格式错误，请检查数据保存的JSON格式！", e);
        } catch (Exception e) {
            logger.error("处理数据异常，请检查你的代码！", e);
        } finally {
            lock.unlock();
        }

    }

    public void saveHbase(ConcurrentHashMap<String, ArrayList<Put>> putMap) {
        for (Map.Entry<String, ArrayList<Put>> entry : putMap.entrySet()) {
            String topic = entry.getKey();
            ArrayList<Put> puts = entry.getValue();
            System.out.println("剩余：hbase data=" + puts.size());
            if (puts.size() > 0) {
                //asynToHbase(puts, Constants.getHtable(topic));
            }
        }

    }

    public void saveES(ConcurrentHashMap<String, ArrayList<IndexRequest>> indexRequestMap) {
        for (Map.Entry<String, ArrayList<IndexRequest>> entry : indexRequestMap.entrySet()) {
            ArrayList<IndexRequest> indexRequests = entry.getValue();
            System.out.println("剩余：esdata=" + indexRequests.size());
            if (indexRequests.size() > 0) {
                // asynToES(indexRequests);
            }
        }

    }

    private void judgeAsynBatch(ArrayList<Put> puts, ArrayList<IndexRequest> indexRequests, String hTable) {
        if (indexRequests.size() >= batchCount) {
            ArrayList<Put> putList = (ArrayList<Put>) puts.clone();
            puts.clear();
            asynToHbase(putList, hTable);
            /****************************************************/
            System.out.println("--------" + batchCount);
            ArrayList<IndexRequest> indexRequestList = (ArrayList<IndexRequest>) indexRequests.clone();
            indexRequests.clear();
            asynToES(indexRequestList);
        }
    }

    /**
     * 异步保存到hbase
     *
     * @param hbaseData
     */
    public void asynToHbase(ArrayList<Put> hbaseData, String hTable) {
        Connection hbaseConn = getHbaseConnection();
        final BufferedMutator.ExceptionListener listener = new BufferedMutator.ExceptionListener() {
            @Override
            public void onException(RetriesExhaustedWithDetailsException e, BufferedMutator mutator) {
                for (int i = 0; i < e.getNumExceptions(); i++) {
                    logger.error("Failed to sent put " + e.getRow(i) + ".");
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
                        returnHbaseConnection(hbaseConn);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            logger.error("exception while creating/destroying Connection or BufferedMutator", e);
        }
    }

    /**
     * 异步保存到es
     *
     * @param esData
     */
    public void asynToES(ArrayList<IndexRequest> esData) {
        TransportClient esConn = getEsConnection();
        BulkRequestBuilder esclientBuk = esService.addEBuk(esConn.prepareBulk(), esData);
        final ActionListener esListener = new ActionListener<BulkResponse>() {
            @Override
            public void onFailure(Exception e) {
                logger.info("ES failure ==>" + e.toString());
            }

            public void onResponse(BulkResponse response) {
                logger.info(response.toString());
            }
        };
        Executors.newFixedThreadPool(30).execute(new Runnable() {
            @Override
            public void run() {
                esclientBuk.execute(esListener);
                esData.clear();
                returnEsConnection(esConn);
            }
        });
    }
}
