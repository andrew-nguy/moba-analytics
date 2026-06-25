package com.nguay097.moba_analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MobaAnalyticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobaAnalyticsApplication.class, args);
	}

}
