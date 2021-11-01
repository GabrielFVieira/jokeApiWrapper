package com.gabrielfigueiredo.jokeApiWrapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabrielfigueiredo.jokeApiWrapper.model.JokeRating;

public interface JokeRatingRepository extends JpaRepository<JokeRating, Integer> {
}
