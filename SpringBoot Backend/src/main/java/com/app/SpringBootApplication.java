package com.app; // Specify the package for the application

import org.modelmapper.ModelMapper; // Import the ModelMapper library for object mapping
import org.springframework.boot.SpringApplication; // Import SpringApplication for launching the application
import org.springframework.context.annotation.Bean; // Import the Bean annotation for defining beans in the application context

// Main class for the Spring Boot application
@org.springframework.boot.autoconfigure.SpringBootApplication
// Annotation to mark this class as a Spring Boot application
public class SpringBootApplication {
    
    // Bean definition for ModelMapper to enable dependency injection
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper(); // Create and return a new instance of ModelMapper
    }

    // Main method to start the Spring Boot application
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args); // Launch the application
    }
}
