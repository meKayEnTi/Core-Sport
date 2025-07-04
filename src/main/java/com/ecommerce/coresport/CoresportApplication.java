package com.ecommerce.coresport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CoresportApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoresportApplication.class, args);
	}

}
