package com.ssafy.ingredientservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class IngredientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngredientServiceApplication.class, args);
	}

}
