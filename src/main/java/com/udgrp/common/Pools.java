
package com.udgrp.common;


import com.udgrp.pools.base.PoolConfig;
import com.udgrp.pools.es.EsConnectionPool;
import com.udgrp.pools.hbase.HbaseConnectionPool;
import org.apache.hadoop.hbase.client.Connection;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kejw
 * @version V1.0
 * @Project ud-kafka-kafka
 * @Description: TODO
 * @date 2017/6/16
 */
abstract public class Pools {
    public static final Logger logger = LoggerFactory.getLogger(Pools.class);
    protected HbaseConnectionPool hbaseConnectionPool;

    protected EsConnectionPool esConnectionPool;

    public PoolConfig getPoolConfig() {
        PoolConfig poolConfig = null;
        if (poolConfig == null) {
            poolConfig = new PoolConfig();
            poolConfig.setMaxTotal(PoolsConfig.MAXTOTAL);
            poolConfig.setMaxIdle(PoolsConfig.MAXIDLE);
            poolConfig.setMaxWaitMillis(PoolsConfig.MAXWAITMILLIS);
            poolConfig.setTestOnBorrow(PoolsConfig.TESTONBORROW);
            poolConfig.setTestOnReturn(PoolsConfig.TESTONRETURN);
            poolConfig.setTestOnCreate(PoolsConfig.TESTONCREATE);
        }
        return poolConfig;
    }

    public synchronized Connection getHbaseConnection() {

        if (hbaseConnectionPool == null || hbaseConnectionPool.isClosed()) {
            hbaseConnectionPool = getHbaseConnectionPool();
        }

        return hbaseConnectionPool.getConnection();
    }

    public synchronized void returnHbaseConnection(Connection connection) {

        hbaseConnectionPool.returnConnection(connection);
    }

    public synchronized TransportClient getEsConnection() {

        if (esConnectionPool == null || esConnectionPool.isClosed()) {
            esConnectionPool = getEsConnectionPool();
        }
        return esConnectionPool.getConnection();

    }

    public synchronized void returnEsConnection(TransportClient connection) {

        esConnectionPool.returnConnection(connection);

    }

    abstract public HbaseConnectionPool getHbaseConnectionPool();

    abstract public EsConnectionPool getEsConnectionPool();
}


