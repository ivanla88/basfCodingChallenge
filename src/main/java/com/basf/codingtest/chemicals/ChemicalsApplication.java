package com.basf.codingtest.chemicals;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
		info = @Info(
				title = "Patents API",
				version = "1.0",
				description = "Patent API to manage documents and database")
		)
public class ChemicalsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChemicalsApplication.class, args);
	}

}
