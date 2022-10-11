package com.hrw.vsproject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class VsProjectApplication {
	private static Logger logger = LoggerFactory.getLogger(VsProjectApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(VsProjectApplication.class, args);

		logger.info("SWAGGER: http://localhost:8080/swagger-ui.html");
	}
}
