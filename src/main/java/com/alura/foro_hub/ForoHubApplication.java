package com.alura.foro_hub;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info =@Info(
				title = "foro-hub",
				version = "version 1",
				description = "API REST para gestionar tópicos (CRUD) utilizando Spring"
		)
)

@SpringBootApplication
public class ForoHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForoHubApplication.class, args);
	}

}
