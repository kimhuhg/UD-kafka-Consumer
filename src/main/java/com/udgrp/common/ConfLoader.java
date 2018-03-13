

package com.udgrp.common;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.udgrp.constant.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author kejw
 * @version V1.0
 * @Project ud-kafka-kafka
 * @Description: TODO
 * @date 2017/10/13
 */
@Component
public class ConfLoader implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(ConfLoader.class);
    public static Config conf;
    @Autowired
    private PoolsImpl pool;

    @Override
    public void run(String... strings) throws Exception {
        logger.info(">>>>>>>>>>>>>>>服务启动执行，执行加配置文件等操作<<<<<<<<<<<<<");
        if (conf == null) {
            //测试环境配置文件加载
            conf = ConfigFactory.load("application.properties");
            //生产环境配置文件加载
            //conf = ConfigFactory.parseFile(new File("/home/jar/application.properties"));
        }
        /**
         * 睡眠确保配置变量都赋值完毕
         */
        Thread.sleep(300);
        /**
         * 初始化全局变量
         */
        for (String topic : Global.CONSUMER_TO_MYSQL) {
            Global.MYSQL_LAST_COUNT.put(topic, 0);
            Global.entityMap.put(topic, new ArrayList<>());
            Global.TIMES.put(topic, 0);
        }
        /**
         * 初始化全局变量
         */
        for (String topic : Global.CONSUMER_TO_HBASE) {
            Global.HBASE_LAST_COUNT.put(topic, 0);
            Global.putMap.put(topic, new ArrayList<>());
            Global.TIMES.put(topic, 0);
        }
        /**
         * 提前初始化连接
         */
        pool.returnHbaseConnection(pool.getHbaseConnection());
    }
}
