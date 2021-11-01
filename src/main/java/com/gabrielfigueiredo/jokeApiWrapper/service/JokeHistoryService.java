package com.gabrielfigueiredo.jokeApiWrapper.service;

import java.util.List;

import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;

public interface JokeHistoryService {
	public Boolean isNewJoke(Joke joke);
	
	public List<Integer> getAllUnratedJokes(String language);
}
