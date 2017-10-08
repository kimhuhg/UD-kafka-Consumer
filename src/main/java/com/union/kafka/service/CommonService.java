package com.union.kafka.service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.union.bean.ExListCarInfo;
import com.union.bean.GPSMissInfo;
import com.union.bean.GPSRecordInfo;
import com.union.common.base.InternalPools;
import com.union.constant.Constants;
import com.union.constant.Global;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

    @Autowired
    private HbaseService hbaseService;
    @Autowired
    private ElasticSearchService esService;

    private static final Lock lock = new ReentrantLock();

    public void prePareGPSData(ConsumerRecord<String, String> msg) {
        try {
            JSONObject jsonObj = JSONObject.parseObject(msg.value());
            GPSRecordInfo gpsRecordInfo = new GPSRecordInfo(jsonObj);
            lock.lock();
            Global.putMap.get(msg.topic()).add(hbaseService.getGPSPut(gpsRecordInfo, msg.key()));
            Global.indexRequestMap.get(msg.topic()).add(esService.getGPSIndexReques(gpsRecordInfo, msg.key()));
            judgeAsynBatch(msg.topic());
        } catch (JSONException e) {
            logger.error("数据格式错误，请检查数据保存的JSON格式！", e);
        } catch (Exception e) {
            logger.error("处理数据异常，请检查你的代码！", e);
        } finally {
            lock.unlock();
        }
    }

    public void prePareSHWData(ConsumerRecord<String, String> msg) {
        try {
            JSONObject jsonObj = JSONObject.parseObject(msg.value());
            ExListCarInfo exListCarInfo = new ExListCarInfo(jsonObj);
            lock.lock();
            Global.putMap.get(msg.topic()).add(hbaseService.getSHWPut(exListCarInfo, msg.key()));
            Global.indexRequestMap.get(msg.topic()).add(esService.getSHWIndexReques(exListCarInfo, msg.key()));
            judgeAsynBatch(msg.topic());
        } catch (JSONException e) {
            logger.error("数据格式错误，请检查数据保存的JSON格式！", e);
        } catch (Exception e) {
            logger.error("处理数据异常，请检查你的代码！", e);
        } finally {
            lock.unlock();
        }

    }
    public void prePareGPSMISSData(ConsumerRecord<String, String> msg) {
        try {
            JSONObject jsonObj = JSONObject.parseObject(msg.value());
            GPSMissInfo gpsMissInfo = new GPSMissInfo(jsonObj);
            lock.lock();
            Global.putMap.get(msg.topic()).add(hbaseService.getGPSMISSPut(gpsMissInfo, msg.key()));
            Global.indexRequestMap.get(msg.topic()).add(esService.getGPSMISSIndexReques(gpsMissInfo, msg.key()));
            judgeAsynBatch(msg.topic());
        } catch (JSONException e) {
            logger.error("数据格式错误，请检查数据保存的JSON格式！", e);
        } catch (Exception e) {
            logger.error("处理数据异常，请检查你的代码！", e);
        } finally {
            lock.unlock();
        }

    }

    public void saveHbase(String hTable, ArrayList<Put> puts) {
        asynToHbase(puts, hTable);
    }

    public void saveEs(ArrayList<IndexRequest> indexRequests) {
        asynToES(indexRequests);
    }


    private void judgeAsynBatch(String topic) {
        if (Global.putMap.get(topic).size() >= Constants.batchCount) {
            ArrayList<Put> putList = (ArrayList<Put>) Global.putMap.get(topic).clone();
            Global.putMap.get(topic).clear();
            asynToHbase(putList, Constants.getHtable(topic));
            /****************************************************/
            ArrayList<IndexRequest> indexRequestList = (ArrayList<IndexRequest>) Global.indexRequestMap.get(topic).clone();
            Global.indexRequestMap.get(topic).clear();
            asynToES(indexRequestList);
            System.out.println("-------------" + Constants.batchCount);
        }
    }

    /**
     * 异步保存到hbase
     *
     * @param hbaseData
     */
    @Async("hbaseAsyn")
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
            System.out.println("hbaseAsyn" + Thread.currentThread().getName());
            final BufferedMutator mutator = hbaseConn.getBufferedMutator(params);
            mutator.mutate(hbaseData);
            mutator.close();
            hbaseData.clear();
            returnHbaseConnection(hbaseConn);
        } catch (IOException e) {
            logger.error("exception while creating/destroying Connection or BufferedMutator", e);
        }
    }

    /**
     * 异步保存到es
     *
     * @param esData
     */
    @Async("esAsyn")
    public void asynToES(ArrayList<IndexRequest> esData) {
        TransportClient esConn = getEsConnection();
        BulkRequestBuilder esclientBuk = esService.addEBuk(esConn.prepareBulk(), esData);
        final ActionListener esListener = new ActionListener<BulkResponse>() {
            @Override
            public void onFailure(Exception e) {
                logger.error("ES failure ==>" + e.toString());
            }

            public void onResponse(BulkResponse response) {
                logger.info(response.toString());
            }
        };
        System.out.println("esAsyn" + Thread.currentThread().getName());
        esclientBuk.execute(esListener);
        esData.clear();
        returnEsConnection(esConn);
    }


/*
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
*/


 /*

 public void saveHbaseEs(String hTable, ArrayList<Put> puts, ArrayList<IndexRequest> indexRequests) {
        asynToHbase(puts, hTable);
        asynToES(indexRequests);
    }
 public void saveHbase(ConcurrentHashMap<String, ArrayList<Put>> putMap) {
         for (Map.Entry<String, ArrayList<Put>> entry : putMap.entrySet()) {
             String topic = entry.getKey();
             ArrayList<Put> puts = entry.getValue();
             System.out.println("topic:" + topic + " 剩余：hbase data=" + puts.size());
             if (puts.size() > 0) {
                 //asynToHbase(puts, Constants.getHtable(topic));
             }
         }
     }

     public void saveES(ConcurrentHashMap<String, ArrayList<IndexRequest>> indexRequestMap) {
         for (Map.Entry<String, ArrayList<IndexRequest>> entry : indexRequestMap.entrySet()) {
             String topic = entry.getKey();
             ArrayList<IndexRequest> indexRequests = entry.getValue();
             System.out.println("topic:" + topic + " 剩余：esdata=" + indexRequests.size());
             if (indexRequests.size() > 0) {
                 //asynToES(indexRequests);
             }
         }
     }
 */

    public long getEsTotal(String topic) {
        Assert.hasLength(topic, "topic must be specified");
        TransportClient esConn = getEsConnection();
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
            logger.error("查询异常：" + e.getMessage());
        } finally {
            returnEsConnection(esConn);
        }
        return count;
    }

    @Test
    public void test() {
        System.out.println(getEsTotal("jtt_gps_topic_rc"));
    }
}
