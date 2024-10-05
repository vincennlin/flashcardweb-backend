package com.vincennlin.courseservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@OpenAPIDefinition(
		info = @Info(
				title = "[course-ws] Flashcardweb 課程微服務 API",
				version = "1.0",
				description = "/api/v1 的前面要加上 course-ws ，例如 http://localhost:8765/course-ws/api/v1",
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
public class CourseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseServiceApplication.class, args);
	}

}
