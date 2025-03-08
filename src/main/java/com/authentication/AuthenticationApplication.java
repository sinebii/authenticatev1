package com.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthenticationApplication {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
		logger.info("*************** Application Started *****************");
	}

}
