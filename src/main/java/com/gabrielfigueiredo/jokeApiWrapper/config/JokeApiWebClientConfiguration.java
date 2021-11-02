package com.gabrielfigueiredo.jokeApiWrapper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class JokeApiWebClientConfiguration {

	@Bean
	public WebClient jokeApiClient(WebClient.Builder builder) {
		return builder.baseUrl("https://v2.jokeapi.dev")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
	}
}
