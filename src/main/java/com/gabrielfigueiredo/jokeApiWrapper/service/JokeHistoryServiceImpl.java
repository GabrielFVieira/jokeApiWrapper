package com.gabrielfigueiredo.jokeApiWrapper.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;
import com.gabrielfigueiredo.jokeApiWrapper.model.JokeHistory;
import com.gabrielfigueiredo.jokeApiWrapper.repository.JokeHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JokeHistoryServiceImpl implements JokeHistoryService {
	private final JokeHistoryRepository repository;
	
	private void save(Joke joke) {
		repository.save(new JokeHistory(joke));
	}
	
	private Optional<JokeHistory> find(Joke joke) {
		return repository.findOne(Example.of(new JokeHistory(joke)));
	}

	@Override
	public Boolean isNewJoke(Joke joke) {
		if(find(joke).isPresent()) {
			return false;
		}
		
		save(joke);
		return true;
	}
	
	public List<Integer> getAllUnratedJokes(String language) {
		
		
		return null;
	}
}
