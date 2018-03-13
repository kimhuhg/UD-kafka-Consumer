

package com.udgrp.mapper.impl;

import com.udgrp.entity.GPSMiss;
import org.springframework.stereotype.Component;

/**
 * @author kejw
 * @version V1.0
 * @Project ud-kafka-consumer
 * @Description: TODO
 * @date 2017/11/28
 */
@Component
public class GPSMissMapperImpl extends BaseMapperImpl<GPSMiss, String> {
    public void saveIfNoExist(GPSMiss entity) {
        try {
            GPSMiss entity1 = selectById(entity.getId());
            if (entity1 == null) {
                insert(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
