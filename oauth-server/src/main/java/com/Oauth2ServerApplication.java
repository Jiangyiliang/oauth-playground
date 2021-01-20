package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com"})
@SpringBootApplication
public class Oauth2ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2ServerApplication.class, args);
	}

}

