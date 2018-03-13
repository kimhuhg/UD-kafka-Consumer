
package com.udgrp.mapper.impl;


import com.udgrp.entity.ExListData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author kejw
 * @version V1.0
 * @Project ud-kafka-consumer
 * @Description: TODO
 * @date 2017/11/28
 */
@Component
public class ExListDataMapperImpl extends BaseMapperImpl<ExListData, String> {
    public void saveIfNoExist(ExListData entity) {
        try {
            ExListData entity1 = selectById(entity.getId());
            if (entity1 == null) {
                insert(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
















