package com.jojo.recovery;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jojo.recovery.mapper")
public class RecoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecoveryApplication.class, args);
    }

}
