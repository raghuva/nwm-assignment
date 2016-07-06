package com.nwm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableEntityLinks;

@SpringBootApplication
@EnableEntityLinks
public class NwmAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(NwmAssignmentApplication.class, args);
	}


}
