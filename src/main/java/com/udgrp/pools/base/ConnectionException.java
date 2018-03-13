

package com.udgrp.pools.base;

/**
 * @Project
 * @Description: TODO
 * @author kejw
 * @date 2017/6/16
 * @version V1.0
 */
public class ConnectionException extends RuntimeException {

    private static final long serialVersionUID = -6503525110247209484L;

    public ConnectionException() {
        super();
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(Throwable e) {
        super(e);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
