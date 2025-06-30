package com.joaopedro.studytask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StudytaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudytaskApplication.class, args);}

}
