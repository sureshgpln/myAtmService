package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {JpaRepositoriesAutoConfiguration.class})
@EnableJpaRepositories
public class AtmCashService {

	private static Class<AtmCashService> AtmCashDispenserClass = AtmCashService.class;

	public static void main(String[] args) {
		SpringApplication.run(AtmCashDispenserClass, args);
	}
}
