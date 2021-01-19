package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ClientbApplication {


    public static void main(String[] args) {
        SpringApplication.run(ClientbApplication.class, args);
    }

//    // 不重写打包war部署到tomcat接口会报404
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(ClientbApplication.class);
//    }


}
