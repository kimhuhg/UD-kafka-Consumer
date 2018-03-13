

package com.udgrp.common;


import com.udgrp.pools.es.EsConnectionPool;
import com.udgrp.pools.hbase.HbaseConnectionPool;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.LinkedList;

/**
 * @Project ud-kafka-kafka
 * @Description: TODO
 * @author kejw
 * @date 2017/6/16
 * @version V1.0
 */
@Component
public class PoolsImpl extends Pools {
    @Override
    public HbaseConnectionPool getHbaseConnectionPool() {
        logger.info("init hbase connection Pool ...");
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", PoolsConfig.HBASE_ZOOKEEPER_QUORUM);
        configuration.set("hbase.zookeeper.property.clientPort", PoolsConfig.HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT);
        configuration.set("zookeeper.znode.parent", PoolsConfig.ZOOKEEPER_ZNODE_PARENT);
        return new HbaseConnectionPool(getPoolConfig(), configuration);
    }

    @Override
    public EsConnectionPool getEsConnectionPool() {
        Settings.Builder settings = Settings.builder();
        settings.put("cluster.name", PoolsConfig.ES_CLUSTER_NAME);

        LinkedList<InetSocketTransportAddress> address = new LinkedList<InetSocketTransportAddress>();
        String[] hosts = PoolsConfig.ES_CLUSTER_URL.split(",");
        for (String host : hosts) {
            String[] hp = host.split(":");
            try {
                address.add(new InetSocketTransportAddress(InetAddress.getByName(hp[0]), Integer.valueOf(hp[1])));
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("getEsConnectionPool fail ..."+e.getMessage());
            }
        }
        return new EsConnectionPool(getPoolConfig(), settings.build(), address);
    }
}
