package com.union.kafka.consumer;

import com.union.constant.Global;
import com.union.kafka.service.CommonService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created by kejw on 2017/9/21.
 */
@Component
public class UDKafkaConsumer {

    @Autowired
    private CommonService service;

    @KafkaListener(topics = "${kafka.gps.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void listenGPS(ConsumerRecord<String, String> msg) throws InterruptedException {
        service.prePareGPSData(msg);
    }

    @KafkaListener(topics = "${kafka.shw.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void listenSHW(ConsumerRecord<String, String> msg) {
        service.prePareSHWData(msg);
    }

    @KafkaListener(topics = "${kafka.gps_miss.topi}", containerFactory = "kafkaListenerContainerFactory")
    public void listenGPSMISS(ConsumerRecord<String, String> msg) {
        service.prePareGPSMISSData(msg);
    }
}
