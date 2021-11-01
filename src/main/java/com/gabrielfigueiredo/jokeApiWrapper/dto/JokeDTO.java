package com.gabrielfigueiredo.jokeApiWrapper.dto;

import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;

import lombok.Data;

@Data
public class JokeDTO {
	private Integer id;
	private String joke;
	
	public JokeDTO(Joke entity) {
		this.id = entity.getJokeApiId();
		this.joke = entity.getJoke();
	}
}
