package com.example.java_playground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.java_playground.domain.model")
public class JavaPlaygroundApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaPlaygroundApplication.class, args);
	}

}
