package com.gabrielfigueiredo.jokeApiWrapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabrielfigueiredo.jokeApiWrapper.model.JokeHistory;

public interface JokeHistoryRepository extends JpaRepository<JokeHistory, Integer> {

}
