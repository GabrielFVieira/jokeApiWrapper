package com.gabrielfigueiredo.jokeApiWrapper.service;

import java.util.List;

import com.gabrielfigueiredo.jokeApiWrapper.exception.JokeNotFoundException;
import com.gabrielfigueiredo.jokeApiWrapper.exception.OutOfJokesException;
import com.gabrielfigueiredo.jokeApiWrapper.exception.ServerException;
import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;
import com.gabrielfigueiredo.jokeApiWrapper.model.enums.JokeType;

public interface JokeService {
	public Joke getJoke(JokeType type, String... categories) throws JokeNotFoundException, OutOfJokesException, ServerException;
	
	public List<Joke> getTopJokes(String category, Integer amount) throws ServerException;
	
	public Joke find(Integer id) throws JokeNotFoundException, ServerException;
	
	public Boolean testConnection();
}
