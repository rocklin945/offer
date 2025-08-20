package com.rocklin.offer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.rocklin.offer.mapper")
public class OfferApplication {
    public static void main(String[] args) {
        SpringApplication.run(OfferApplication.class, args);
    }

}
