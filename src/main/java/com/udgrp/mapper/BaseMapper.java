

package com.udgrp.mapper;

import com.udgrp.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author kejw
 * @version V1.0
 * @Project ud-kafka-consumer
 * @Description: TODO
 * @date 2017/11/28
 */
public interface BaseMapper<T extends BaseEntity, PK extends Serializable> {

    /**
     * 插入一条数据
     *
     * @param entity
     */
    PK insert(T entity) throws Exception;

    /**
     * 批量插入
     *
     * @param entitys
     */
    void batchInsert(List<T> entitys);

    /**
     * 通过Id查询
     *
     * @param id
     * @return
     */
    T selectById(PK id) throws Exception;

    /**
     * 异步批量保存
     * @param entitys
     */
    void asynBatchInsert(List<T> entitys);
}



















