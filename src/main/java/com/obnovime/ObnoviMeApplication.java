package com.obnovime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ObnoviMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObnoviMeApplication.class, args);
	}

}
