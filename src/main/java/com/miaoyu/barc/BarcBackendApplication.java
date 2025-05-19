package com.miaoyu.barc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan({
        "com.miaoyu.barc.api.mapper",
        "com.miaoyu.barc.user.mapper",
        "com.miaoyu.barc.comment.mapper",
        "com.miaoyu.barc.feedback.mapper"
})
@EnableScheduling
@SpringBootApplication
public class BarcBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarcBackendApplication.class, args);
    }

}
