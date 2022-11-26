package com.purpleprint.logserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LogServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogServerApplication.class, args);
	}

}
