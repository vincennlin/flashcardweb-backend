package com.vincennlin.flashcardwebbackend;

import com.vincennlin.flashcardwebbackend.entity.Role;
import com.vincennlin.flashcardwebbackend.repository.RoleRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Flashcardweb 後端 API",
                version = "1.0",
                description = "Flashcardweb Backend API",
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
public class FlashcardwebBackendApplication implements CommandLineRunner {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(FlashcardwebBackendApplication.class, args);
    }

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }
    }
}
