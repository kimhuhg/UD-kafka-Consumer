package com.union.common.base;


import com.union.common.constant.Constants;
import com.union.common.es.EsConnectionPool;
import com.union.common.hbase.HbaseConnectionPool;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.util.LinkedList;

/**
 * Created by kejw on 2017/6/16.
 */
public class InternalPools extends Pools {
    @Override
    public HbaseConnectionPool getHbaseConnectionPool() {
        System.out.println("----------------------init hbase");
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", Constants.HBASE_ZOOKEEPER_QUORUM);
        configuration.set("hbase.zookeeper.property.clientPort",Constants.HBASE_ZOOKEEPER_PROPERTY_CLIENTPOINT);
        configuration.set("zookeeper.znode.parent", Constants.ZOOKEEPER_ZNODE_PARENT);
        return new HbaseConnectionPool(getPoolConfig(), configuration);
    }

    @Override
    public EsConnectionPool getEsConnectionPool() {
        System.out.println("----------------------init ES");
        Settings.Builder settings = Settings.builder();
        settings.put("cluster.name", Constants.ES_CLUSTER_NAME);

        LinkedList<InetSocketTransportAddress> address = new LinkedList<InetSocketTransportAddress>();
        String[] hosts = Constants.ES_URL.split(",");
        for (String host : hosts) {
            String[] hp = host.split(":");
            try {
                address.add(new InetSocketTransportAddress(InetAddress.getByName(hp[0]), Integer.valueOf(hp[1])));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new EsConnectionPool(getPoolConfig(), settings.build(), address);
    }
}
