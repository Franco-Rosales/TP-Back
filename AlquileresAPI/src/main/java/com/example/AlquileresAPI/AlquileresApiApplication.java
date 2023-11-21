package com.example.AlquileresAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.example.AlquileresAPI.Entities", "com.example.EstacionesAPI.Entities"})

public class AlquileresApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlquileresApiApplication.class, args);
	}

}
