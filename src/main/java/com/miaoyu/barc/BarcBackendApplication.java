package com.miaoyu.barc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({
        "com.miaoyu.barc.api.mapper"
})
@SpringBootApplication
public class BarcBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarcBackendApplication.class, args);
    }

}
