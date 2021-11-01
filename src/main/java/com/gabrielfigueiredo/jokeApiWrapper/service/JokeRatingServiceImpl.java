package com.gabrielfigueiredo.jokeApiWrapper.service;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;
import com.gabrielfigueiredo.jokeApiWrapper.model.JokeRating;
import com.gabrielfigueiredo.jokeApiWrapper.repository.JokeRatingRepository;

@Service
public class JokeRatingServiceImpl implements JokeRatingService {
	private final JokeRatingRepository repository;
	private final JokeService jokeService;
	
	public JokeRatingServiceImpl(JokeRatingRepository repository, @Lazy JokeService jokeService) {
		this.repository = repository;
		this.jokeService = jokeService;
	}
	
	@Override
	public void createRating(Integer jokeId, String language, Integer rating) {
		createRating(jokeId, language, rating, null);
	}
	
	@Override
	public void createRating(Integer jokeId, String language, Integer rating, String comment) {
		Joke joke = jokeService.find(jokeId, language);
		repository.save(new JokeRating(joke, rating, comment));
	}
	
	@Override
	public List<JokeRating> list(Integer jokeId, String language) {
		Joke joke = jokeService.find(jokeId, language);
		JokeRating jokeRating = new JokeRating(joke, null, null);
		return repository.findAll(Example.of(jokeRating));
	}
}
