package com.project.JewelHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JewelHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(JewelHubApplication.class, args);
	}

}
