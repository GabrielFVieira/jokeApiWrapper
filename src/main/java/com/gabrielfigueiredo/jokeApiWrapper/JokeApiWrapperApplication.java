package com.gabrielfigueiredo.jokeApiWrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class JokeApiWrapperApplication extends SpringBootServletInitializer {

	@Bean
	public WebClient jokeApiClient(WebClient.Builder builder) {
		return builder.baseUrl("https://v2.jokeapi.dev")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(JokeApiWrapperApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(JokeApiWrapperApplication.class);
	}
}
