package com.gabrielfigueiredo.jokeApiWrapper.service;

import java.util.List;

import com.gabrielfigueiredo.jokeApiWrapper.exception.JokeNotFoundException;
import com.gabrielfigueiredo.jokeApiWrapper.model.JokeRating;

public interface JokeRatingService {
	
	public void createRating(Integer id, Integer rating) throws JokeNotFoundException;
	
	public void createRating(Integer id, Integer rating, String comment) throws JokeNotFoundException;
	
	public List<JokeRating> listAll();
	
	public List<JokeRating> list(Integer id);
	
	public List<JokeRating> listByRating(String category, Integer amount);
}
