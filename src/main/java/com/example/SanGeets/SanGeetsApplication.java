package com.example.SanGeets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SanGeetsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SanGeetsApplication.class, args);
	}

}
