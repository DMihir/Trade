package com.barclays.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.log4j.Log4j2;

/**
 * @author Mihir
 *
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@Log4j2
public class ApplicationRun {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationRun.class, args);
		log.info("Application Started !");
	}

}
