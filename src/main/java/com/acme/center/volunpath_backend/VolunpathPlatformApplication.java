package com.acme.center.volunpath_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VolunpathPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(VolunpathPlatformApplication.class, args);
	}

}
