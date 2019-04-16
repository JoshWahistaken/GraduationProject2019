package com.edu.nju.joshua.wedesign;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@MapperScan(value="com.edu.nju.joshua.wedesign.mapper")
@SpringBootApplication
public class WedesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(WedesignApplication.class, args);
    }

}
