package com.union.common.es;

import com.union.common.base.ConnectionPool;
import com.union.common.base.PoolBase;
import com.union.common.base.PoolConfig;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.plugins.Plugin;

import java.util.Collection;


/**
 * Created by kejw on 2017/6/16.
 */
public class EsConnectionPool extends PoolBase<TransportClient> implements ConnectionPool<TransportClient> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -9126420905798370263L;


    public EsConnectionPool(final PoolConfig poolConfig, final Settings settings, Collection<InetSocketTransportAddress> transportAddress, Class<? extends Plugin>... plugins){

       super(poolConfig , new EsConnectionFactory(settings,transportAddress,plugins));

    }



    @Override
    public TransportClient getConnection() {

        return super.getResource();
    }

    @Override
    public void returnConnection(TransportClient client) {

        super.returnResource(client);
    }

    @Override
    public void invalidateConnection(TransportClient client) {

        super.invalidateResource(client);
    }

}