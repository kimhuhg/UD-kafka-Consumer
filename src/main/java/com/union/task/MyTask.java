package com.union.task;

import com.union.constant.Constants;
import com.union.constant.Global;
import com.union.kafka.service.CommonService;
import org.apache.hadoop.hbase.client.Put;
import org.elasticsearch.action.index.IndexRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

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
    public void cronSaveHbaseES() {
        for (String topic : Global.topics) {
            int hbcount = Global.hbaseCountMap.get(topic);
            ArrayList<Put> lastPuts = Global.putMap.get(topic);
            Global.hbaseCountMap.put(topic, lastPuts.size());

            int escount = Global.esCountMap.get(topic);
            ArrayList<IndexRequest> lastIndexRequests = Global.indexRequestMap.get(topic);
            Global.esCountMap.put(topic, lastIndexRequests.size());

            System.out.println(topic + " 上次hbasedata:" + hbcount + " 上次esdata:" + escount);
            System.out.println(topic + " 这次hbasedata:" + lastPuts.size() + " 这次esdata:" + lastIndexRequests.size());
            System.out.println("-------------------------------------------------------------------------");
            if (lastIndexRequests.size() == escount && lastIndexRequests.size() > 0) {
                logger.info("开始处理" + topic + " 剩余的数据........esdata:" + lastIndexRequests.size());
                service.saveEs((ArrayList<IndexRequest>) lastIndexRequests.clone());
                Global.esCountMap.put(topic, 0);
            }
            if (lastPuts.size() == hbcount && lastPuts.size() > 0) {
                logger.info("开始处理" + topic + " 剩余的数据........hbasedata:" + lastPuts.size());
                service.saveHbase(Constants.getHtable(topic), (ArrayList<Put>) lastPuts.clone());
                long curEsdata = service.getEsTotal(topic);
                long lastEsdata = Global.lastEsTotal.get(topic);
                logger.info("上次es:" + Global.lastEsTotal.get(topic) + " 本次es:" + curEsdata);
                if (lastEsdata == curEsdata) {
                    int count = Global.times.get(topic) + 1;
                    Global.times.put(topic, count);
                    //System.out.println("Global.times++");
                } else {
                    Global.lastEsTotal.put(topic, curEsdata);
                    Global.times.put(topic, 0);
                }
                if (Global.times.get(topic) >= 3) {
                    //System.out.println("Global.times:" + Global.times + " 开始清除。。。");
                    Global.indexRequestMap.get(topic).clear();
                    Global.putMap.get(topic).clear();
                    Global.times.put(topic, 0);
                }
                Global.hbaseCountMap.put(topic, 0);
                //System.out.println("Global.times:" + Global.times);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        /**初始化全局变量**/
        for (String topic : Global.topics) {
            Global.hbaseCountMap.put(topic, 0);
            Global.esCountMap.put(topic, 0);
            Global.putMap.put(topic, new ArrayList<>());
            Global.indexRequestMap.put(topic, new ArrayList<>());

            Global.lastEsTotal.put(topic, service.getEsTotal(topic));
            Global.times.put(topic, 0);
        }
        logger.info("定时器启动后初始化........");
    }
}
