/*
 * Copyright @ 2017. 联合数据 粤ICP备17043086号-2 版权所有
 */

package com.udgrp.entity;

import com.udgrp.utils.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kejw
 * @version V1.0
 * @Project ud-kafka-consumer
 * @Description: TODO
 * @date 2017/11/27
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 2368417877456900821L;
    protected String id;
    protected String creator = "sys";
    protected String created_date = DateUtil.dateToStr(new Date());
    protected String modifier = "sys";
    protected String last_updated_date = DateUtil.dateToStr(new Date());
    protected String is_enable = "1";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getLast_updated_date() {
        return last_updated_date;
    }

    public void setLast_updated_date(String last_updated_date) {
        this.last_updated_date = last_updated_date;
    }

    public String getIs_enable() {
        return is_enable;
    }

    public void setIs_enable(String is_enable) {
        this.is_enable = is_enable;
    }
}
