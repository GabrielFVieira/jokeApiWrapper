package com.gabrielfigueiredo.jokeApiWrapper.service;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gabrielfigueiredo.jokeApiWrapper.exception.JokeNotFoundException;
import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;
import com.gabrielfigueiredo.jokeApiWrapper.model.JokeRating;
import com.gabrielfigueiredo.jokeApiWrapper.repository.JokeRatingRepository;
import com.gabrielfigueiredo.jokeApiWrapper.util.JokeApiUtils;

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
		if(joke == null) {
			throw new JokeNotFoundException(getNotFoundMessage(jokeId, language));
		}
		
		repository.save(new JokeRating(joke.getId(), joke.getLang(), joke.getCategory(), rating, comment));
	}
	
	@Override
	public List<JokeRating> list(Integer jokeId, String language) {
		Joke joke = jokeService.find(jokeId, language);
		if(joke == null) {
			throw new JokeNotFoundException(getNotFoundMessage(jokeId, language));
		}
		
		JokeRating jokeRating = new JokeRating(joke.getId(), joke.getLang(), joke.getCategory(), null, null);
		return repository.findAll(Example.of(jokeRating));
	}

	@Override
	public List<JokeRating> listByRatingPerCategory(String category, String language, Integer amount) {
		Pageable limit = Pageable.ofSize(amount);
		
		if(JokeApiUtils.DEFAULT_CATEGORY.equals(category.toLowerCase())) {
			return repository.listTopRankings(language, limit);
		}
		
		return repository.listTopRankings(category, language, limit);
	}
	
	private String getNotFoundMessage(Integer jokeId, String language) {
		return "Joke with id: " + jokeId + " not found in the language: " + language;
	}
}
