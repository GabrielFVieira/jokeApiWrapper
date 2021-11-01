package com.gabrielfigueiredo.jokeApiWrapper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gabrielfigueiredo.jokeApiWrapper.model.JokeHistory;

public interface JokeHistoryRepository extends JpaRepository<JokeHistory, Integer> {
	
	@Query(value= "SELECT jh.jokeId FROM JokeHistory jh WHERE jh.language = :lang AND "
				+ "jh.jokeId NOT IN (SELECT jr.jokeId FROM JokeRating jr WHERE jr.language = :lang)")
	public List<Integer> getAllUnratedJokes(@Param("lang") String language);
}
