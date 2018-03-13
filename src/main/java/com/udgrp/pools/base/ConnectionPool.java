

package com.udgrp.pools.base;

import java.io.Serializable;

/**
 * @Project
 * @Description: TODO
 * @author kejw
 * @date 2017/6/16
 * @version V1.0
 */
public interface ConnectionPool<T> extends Serializable {


    public abstract T getConnection();

    public void returnConnection(T conn);


    public void invalidateConnection(T conn);
}
