package com.union.kafka.consumer;

import com.union.kafka.service.CommonService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.elasticsearch.action.index.IndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kejw on 2017/9/21.
 */
@Component
public class UDKafkaConsumer {

    public static ConcurrentHashMap<String, ArrayList<Put>> putMap = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, ArrayList<IndexRequest>> indexRequestMap = new ConcurrentHashMap<>();

    @Autowired
    private CommonService service;

    @KafkaListener(topics = "${kafka.gps.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void listenGPS(ConsumerRecord<String, String> msg, Acknowledgment ack) {
        ArrayList<Put> puts = putMap.putIfAbsent(msg.topic(), new ArrayList<>());
        ArrayList<IndexRequest> indexRequests = indexRequestMap.putIfAbsent(msg.topic(), new ArrayList<>());
        service.prePareGPSData(msg, puts, indexRequests);
        ack.acknowledge();
    }

    @KafkaListener(topics = "${kafka.shw.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void listenSHW(ConsumerRecord<String, String> msg, Acknowledgment ack) {
        ArrayList<Put> puts = putMap.putIfAbsent(msg.topic(), new ArrayList<>());
        ArrayList<IndexRequest> indexRequests = indexRequestMap.putIfAbsent(msg.topic(), new ArrayList<>());
        service.prePareSHWData(msg, puts, indexRequests);
        ack.acknowledge();
    }
}
