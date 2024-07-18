package com.vincennlin.flashcardservice;

import feign.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDiscoveryClient
@EnableFeignClients
@EntityScan(basePackages = {"com.vincennlin.flashcardservice.entity", "com.vincennlin.noteservice.entity", "com.vincennlin.userservice.entity"})
@EnableJpaRepositories(basePackages = {"com.vincennlin.flashcardservice.repository", "com.vincennlin.noteservice.repository", "com.vincennlin.userservice.repository"})
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
