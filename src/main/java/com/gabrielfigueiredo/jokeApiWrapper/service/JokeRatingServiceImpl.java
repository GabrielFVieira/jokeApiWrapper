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

@Service
public class JokeRatingServiceImpl implements JokeRatingService {
	private final JokeRatingRepository repository;
	private final JokeService jokeService;
	
	public JokeRatingServiceImpl(JokeRatingRepository repository, @Lazy JokeService jokeService) {
		this.repository = repository;
		this.jokeService = jokeService;
	}
	
	@Override
	public void createRating(Integer jokeId, Integer rating) {
		createRating(jokeId, rating, null);
	}
	
	@Override
	public void createRating(Integer jokeId, Integer rating, String comment) {
		Joke joke = jokeService.find(jokeId);
		if(joke == null) {
			throw new JokeNotFoundException("Invalid Joke Id");
		}
		
		repository.save(new JokeRating(jokeId, joke.getLang(), joke.getCategory(), rating, comment));
	}
	
	@Override
	public List<JokeRating> listAll() {
		return repository.findAll();
	}
	
	@Override
	public List<JokeRating> list(Integer id) {
		JokeRating jokeRating = new JokeRating();
		jokeRating.setJokeId(id);
		return repository.findAll(Example.of(jokeRating));
	}

	@Override
	public List<JokeRating> listByRating(String category, Integer amount) {
		Pageable limit = Pageable.ofSize(amount);
		
		if(category == null) {
			return repository.listTopRankings(limit);
		}
		
		return repository.listTopRankings(category, limit);
	}
}
