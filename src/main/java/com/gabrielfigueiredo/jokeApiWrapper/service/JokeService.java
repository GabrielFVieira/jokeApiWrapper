package com.gabrielfigueiredo.jokeApiWrapper.service;

import com.gabrielfigueiredo.jokeApiWrapper.exception.JokeNotFoundException;
import com.gabrielfigueiredo.jokeApiWrapper.exception.ServerException;
import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;
import com.gabrielfigueiredo.jokeApiWrapper.model.enums.JokeType;

public interface JokeService {
	public Joke getJoke(JokeType type, String... categories) throws JokeNotFoundException, ServerException;
	
	public Boolean testConnection();
}
