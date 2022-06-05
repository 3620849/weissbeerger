package com.weissbeerger.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WeissbeergerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeissbeergerApplication.class, args);
	}
}
