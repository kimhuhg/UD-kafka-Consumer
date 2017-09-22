package com.union.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by kejw on 2017/9/21.
 */
@SpringBootApplication(scanBasePackages={"com.union"})
public class ConsumerStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerStartApplication.class, args);
    }
}
