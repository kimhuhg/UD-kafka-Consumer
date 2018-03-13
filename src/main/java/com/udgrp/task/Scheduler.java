

package com.udgrp.task;

import com.udgrp.constant.Constants;
import com.udgrp.constant.Global;
import com.udgrp.entity.BaseEntity;
import com.udgrp.service.CommonService;
import org.apache.hadoop.hbase.client.Put;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author kejw
 * @version V1.0
 * @Project ud-kafka-consumer
 * @Description: TODO
 * @date 2017/9/19
 */
@Component
@EnableScheduling
public class Scheduler implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    private CommonService service;

    @Scheduled(cron = "${schedule.save}")
    public void lastBatch() {
        for (String topic : Global.CONSUMER_TO_MYSQL) {
            lastBatchToMysql(topic);
        }
        for (String topic : Global.CONSUMER_TO_HBASE) {
            lastBatchToHbase(topic);
        }
    }

    private void lastBatchToMysql(String topic) {
        ArrayList<BaseEntity> entitys = Global.entityMap.get(topic);
        int nowCount = entitys.size();
        int lastCount = Global.MYSQL_LAST_COUNT.get(topic);
        Global.MYSQL_LAST_COUNT.put(topic, nowCount);
        logger.info(topic + " 上次mysqldata:" + lastCount + " 这次mysqldata:" + nowCount);
        if (nowCount == lastCount && nowCount > 0) {
            service.saveMysql((ArrayList<BaseEntity>) entitys.clone());
            int count = Global.TIMES.get(topic) + 1;
            Global.TIMES.put(topic, count);
            if (Global.TIMES.get(topic) >= 3) {
                Global.entityMap.get(topic).clear();
                Global.MYSQL_LAST_COUNT.put(topic, 0);
                Global.TIMES.put(topic, 0);
            }
        }
    }

    private void lastBatchToHbase(String topic) {
        ArrayList<Put> puts = Global.putMap.get(topic);
        int nowCount = puts.size();
        int lastCount = Global.HBASE_LAST_COUNT.get(topic);
        Global.HBASE_LAST_COUNT.put(topic, nowCount);
        logger.info(topic + " 上次hbasedata:" + lastCount + " 这次hbasedata:" + nowCount);
        if (nowCount == lastCount && nowCount > 0) {
            service.saveHbase(Constants.getHtable(topic), (ArrayList<Put>) puts.clone());
            int count = Global.TIMES.get(topic) + 1;
            Global.TIMES.put(topic, count);
            if (Global.TIMES.get(topic) >= 3) {
                Global.putMap.get(topic).clear();
                Global.HBASE_LAST_COUNT.put(topic, 0);
                Global.TIMES.put(topic, 0);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
