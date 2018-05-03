package com.wutong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot方式启动类
 *
 */
@SpringBootApplication
public class WutongBackApplication {

    protected final static Logger logger = LoggerFactory.getLogger(WutongBackApplication.class);

   

    public static void main(String[] args) {
        SpringApplication.run(WutongBackApplication.class, args);
        logger.info("Application is success!");
    }
}
