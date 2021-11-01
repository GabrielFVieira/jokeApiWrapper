package com.gabrielfigueiredo.jokeApiWrapper.service;

import java.util.List;
import java.util.Optional;

import com.gabrielfigueiredo.jokeApiWrapper.exception.JokeNotFoundException;
import com.gabrielfigueiredo.jokeApiWrapper.exception.OutOfJokesException;
import com.gabrielfigueiredo.jokeApiWrapper.exception.ServerException;
import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;

public interface JokeService {
	public Joke getJoke(Optional<String> type, String language, String... categories) throws JokeNotFoundException, OutOfJokesException, ServerException;
	
	public List<Joke> getTopJokes(String category, String language) throws ServerException;
	
	public List<Joke> getTopJokes(String category, String language, Integer amount) throws ServerException;
	
	public Joke find(Integer id, String language) throws JokeNotFoundException, ServerException;
	
	public Boolean testConnection();
}
