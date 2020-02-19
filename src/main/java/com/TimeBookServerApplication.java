package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 *  spring boot启动类
 */

//@ServletComponentScan
@SpringBootApplication
public class TimeBookServerApplication {
    public static void main(String[] args){
        SpringApplication.run(TimeBookServerApplication.class,args);
    }
}

