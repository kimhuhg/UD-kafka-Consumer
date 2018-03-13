package com.udgrp.kafka;

import com.udgrp.service.CommonService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author kejw
 * @version V1.0
 * @Project ud-kafka-kafka
 * @Description: TODO
 * @date 2017/9/21
 */
@Component
public class UDKafkaConsumer {

    @Autowired
    private CommonService service;

    @KafkaListener(topics = "${KAFKA_GPS_TOPIC}", containerFactory = "kafkaListenerContainerFactory")
    public void listenGPS(ConsumerRecord<String, String> msg) throws InterruptedException {
        //  System.out.println("msg.topic:"+msg.topic()+" msg.partition:"+msg.partition()+" msg.offset:"+msg.offset()+" currentThread:"+Thread.currentThread().getName());
        service.consumerGPSData(msg);
    }

    @KafkaListener(topics = "${KAFKA_SHW_TOPIC}", containerFactory = "kafkaListenerContainerFactory")
    public void listenSHW(ConsumerRecord<String, String> msg) {
        service.consumerSHWData(msg);
    }

    @KafkaListener(topics = "${KAFKA_GPS_MISS_TOPIC}", containerFactory = "kafkaListenerContainerFactory")
    public void listenGPSMISS(ConsumerRecord<String, String> msg) {
        //System.out.println("msg.topic:"+msg.topic()+" msg.partition:"+msg.partition()+" msg.offset:"+msg.offset()+" currentThread:"+Thread.currentThread().getName());
        service.consumerGPSMISSData(msg);
    }
}
