package com.political_news_service.political_news_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PoliticalNewsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoliticalNewsServiceApplication.class, args);
	}

}
