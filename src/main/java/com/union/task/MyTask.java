package com.union.task;

import com.union.kafka.consumer.UDKafkaConsumer;
import com.union.kafka.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by kejw on 2017/9/19.
 */
@Component
@EnableScheduling
public class MyTask implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MyTask.class);

    @Autowired
    private CommonService service;

    @Scheduled(cron = "${task.save}")
    public void cronSave() {
        service.saveES(UDKafkaConsumer.indexRequestMap);
        service.saveHbase(UDKafkaConsumer.putMap);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("定时器启动后初始化........");
        System.out.println("定时器启动后初始化........");
    }
}
