
package com.udgrp.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.UUID;

/**
 * @author kejw
 * @version V1.0
 * @Project ud-kafka-consumer
 * @Description: TODO
 * @date 2017/11/27
 */
public class Utils {
    /**
     * UUID生成数据库主键ID值
     *
     * @return
     */
    public static String generateId() {
        String s = UUID.randomUUID().toString();
        return s.replaceAll("-", "");
    }

    /**
     * 车牌和时间生成数据库主键ID值
     * @param plate
     * @param time
     * @return
     */
    public static String getId(String plate, String time) {
        long timeStamp = DateUtil.getTimeInMillis(time) / 1000;
        String id = HashMD5.MD5(plate) + "|" + String.valueOf(timeStamp);
        return id;
    }

    /**
     * 获取接口的泛型类型，如果不存在则返回null
     *
     * @param clazz
     * @return
     */
    public static Class<?> getGenericClass(Class<?> clazz) {
        Type t = clazz.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ( (ParameterizedType) t ).getActualTypeArguments();
            return ( (Class<?>) p[0] );
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.print(generateId());
    }
}
