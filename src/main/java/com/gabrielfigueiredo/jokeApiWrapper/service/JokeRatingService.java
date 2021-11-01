package com.gabrielfigueiredo.jokeApiWrapper.service;

import java.util.List;

import com.gabrielfigueiredo.jokeApiWrapper.exception.JokeNotFoundException;
import com.gabrielfigueiredo.jokeApiWrapper.model.JokeRating;

public interface JokeRatingService {
	
	public void createRating(Integer jokeId, String language, Integer rating) throws JokeNotFoundException;
	
	public void createRating(Integer jokeId, String language, Integer rating, String comment) throws JokeNotFoundException;
	
	public List<JokeRating> list(Integer jokeId, String language) throws JokeNotFoundException;
}
