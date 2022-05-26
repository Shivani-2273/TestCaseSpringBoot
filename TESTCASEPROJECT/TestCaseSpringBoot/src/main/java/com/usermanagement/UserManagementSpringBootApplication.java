package com.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.usermanagement.DAO")
@SpringBootApplication
public class UserManagementSpringBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserManagementSpringBootApplication.class, args);
	}
	
	

}
