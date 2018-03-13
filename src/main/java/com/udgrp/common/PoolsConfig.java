

package com.udgrp.common;

import static com.udgrp.common.ConfLoader.conf;

/**
 * @author kejw
 * @version V1.0
 * @Project ud-kafka-consumer
 * @Description: TODO
 * @date 2017/10/20
 */
public class PoolsConfig {
    /**
     * POOLCONF配置参数
     */
    public static final int MAXIDLE = conf.getInt("maxIdle");
    public static final int MAXTOTAL = conf.getInt("maxTotal");
    public static final int MAXWAITMILLIS = conf.getInt("maxWaitMillis");
    public static final boolean TESTONBORROW = conf.getBoolean("testOnBorrow");
    public static final boolean TESTONRETURN = conf.getBoolean("testOnReturn");
    public static final boolean TESTONCREATE = conf.getBoolean("testOnCreate");

    /**
     * ES配置参数
     */
    public static final String ES_CLUSTER_NAME = conf.getString("ES_CLUSTER_NAME");
    public static final String ES_CLUSTER_URL = conf.getString("ES_CLUSTER_URL");

    /**
     * HABSE配置参数
     */
    public static final String HBASE_ZOOKEEPER_QUORUM = conf.getString("HBASE_ZOOKEEPER_QUORUM");
    public static final String HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT = conf.getString("HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT");
    public static final String ZOOKEEPER_ZNODE_PARENT = conf.getString("ZOOKEEPER_ZNODE_PARENT");
}
