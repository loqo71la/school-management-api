package dev.loqo71la.schoolmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Implements an API to manage and assign students to clazzes.
 */
@SpringBootApplication
public class SchoolManagementApplication {

    /**
     * Entry point for executing a Spring Boot application.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(SchoolManagementApplication.class, args);
    }
}
