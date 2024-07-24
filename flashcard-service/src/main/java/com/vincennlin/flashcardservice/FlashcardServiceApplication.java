package com.vincennlin.flashcardservice;

import feign.Logger;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(
		info = @Info(
				title = "[flashcard-ws] Flashcardweb 字卡微服務 API",
				version = "1.0",
				description = "/api/v1 的前面要加上 flashcard-ws ，例如 http://localhost:8765/flashcard-ws/api/v1",
				contact = @io.swagger.v3.oas.annotations.info.Contact(
						name = "vincennlin",
						email = "vincentagwa@gmail.com",
						url = "https://github.com/vincennlin"
				),
				license = @License(
						name = "Apache 2.0",
						url = "http://www.apache.org/licenses/LICENSE-2.0"
				)
		),
		externalDocs = @io.swagger.v3.oas.annotations.ExternalDocumentation(
				description = "Flashcard Web Backend API Documentation",
				url = "https://github.com/vincennlin/flashcardweb-backend"
		)
)
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class FlashcardServiceApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

	public static void main(String[] args) {
		SpringApplication.run(FlashcardServiceApplication.class, args);
	}

}
