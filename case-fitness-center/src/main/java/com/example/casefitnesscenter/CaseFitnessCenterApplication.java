package com.example.casefitnesscenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CaseFitnessCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaseFitnessCenterApplication.class, args);
	}

}
