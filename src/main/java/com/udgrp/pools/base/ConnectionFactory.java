

package com.udgrp.pools.base;


import org.apache.commons.pool2.PooledObjectFactory;

import java.io.Serializable;

/**
 * @Project
 * @Description: TODO
 * @author kejw
 * @date 2017/6/16
 * @version V1.0
 */
public interface ConnectionFactory<T> extends PooledObjectFactory<T>, Serializable {

    public abstract T createConnection() throws Exception;
}
