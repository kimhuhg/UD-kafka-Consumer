package com.udgrp.kafka;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Project ud-kafka-kafka
 * @Description: TODO
 * @author kejw
 * @date 2017/9/21
 * @version V1.0
 */
@SpringBootApplication(scanBasePackages = {"com.udgrp"})
@MapperScan("com.udgrp.mapper")
public class ApplicationStart {

    public static void main(String[] args) {
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
        System.setProperty("javax.xml.parsers.SAXParserFactory", "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");
        SpringApplication.run(ApplicationStart.class, args);
    }
}
