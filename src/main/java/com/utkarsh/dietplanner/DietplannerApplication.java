package com.utkarsh.dietplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DietplannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DietplannerApplication.class, args);
	}

}