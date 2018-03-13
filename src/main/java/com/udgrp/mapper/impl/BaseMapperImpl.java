
package com.udgrp.mapper.impl;

import com.udgrp.entity.BaseEntity;
import com.udgrp.entity.ExListData;
import com.udgrp.mapper.BaseMapper;
import com.udgrp.utils.Utils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author kejw
 * @version V1.0
 * @Project ud-kafka-consumer
 * @Description: TODO
 * @date 2017/11/28
 */
public class BaseMapperImpl<T extends BaseEntity, PK extends Serializable> implements BaseMapper<T, PK> {

    protected static Logger logger = LoggerFactory.getLogger(BaseMapperImpl.class);
    @Resource
    protected SqlSessionTemplate sqlSession;

    private final static String SQLNAME_INSERT = "insert";
    private final static String SQLNAME_BATCHINSERT = "batchInsert";
    private final static String SQLNAME_SELECT = "select";
    private final static String SQLNAME_SEPARATOR = ".";

    private String sqlMapperNamespace = getDefaultSqlMapperNamespace();

    private String getDefaultSqlMapperNamespace() {
        Class<?> genericClass = Utils.getGenericClass(this.getClass());
        return genericClass == null ? null : genericClass.getName();
    }

    protected String getSqlName(String sqlName) {
        return sqlMapperNamespace + SQLNAME_SEPARATOR + sqlName;
    }

    @Override
    public PK insert(T entity) throws Exception {
        try {
            Assert.notNull(entity, "the object argument must not be null");
            sqlSession.insert(getSqlName(SQLNAME_INSERT), entity);
            return (PK) entity.getId();
        } catch (Exception e) {
            logger.error(String.format("保存对象出错！语句：%s", "insert"), e);
            throw new Exception(e);
        }
    }

    @Override
    public void batchInsert(List<T> entitys) {
        try {
            Assert.notNull(entitys, "the object argument must not be null");
            sqlSession.insert(getSqlName(SQLNAME_BATCHINSERT), entitys);
        } catch (Exception e) {
            logger.error(String.format("批量插入对象出错！语句：%s", "batchInsert"), e);
        }
    }

    @Override
    public T selectById(PK id) throws Exception {
        try {
            Assert.notNull(id, "the object argument must not be null");
            return sqlSession.selectOne(getSqlName(SQLNAME_SELECT), id);
        } catch (Exception e) {
            logger.error(String.format("根据ID查询对象出错！语句：%s", "select"), e);
            throw new Exception(e);
        }
    }

    @Override
    public void asynBatchInsert(List<T> entitys) {
        Executors.newFixedThreadPool(20).execute(new Runnable() {
            @Override
            public void run() {
                batchInsert(entitys);
            }
        });
    }
}















