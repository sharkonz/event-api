package com.alfabet.eventapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.alfabet.eventapi"})
public class AlfaBetEventsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlfaBetEventsApiApplication.class, args);
	}

}
