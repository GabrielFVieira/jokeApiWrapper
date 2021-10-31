package com.gabrielfigueiredo.jokeApiWrapper.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gabrielfigueiredo.jokeApiWrapper.model.JokeRating;

public interface JokeRatingRepository extends JpaRepository<JokeRating, Integer> {

	@Query(value= "SELECT j FROM JokeRating j WHERE j.id IN "
			+ "(SELECT jr.id FROM JokeRating jr WHERE jr.category = :category GROUP BY jr.jokeId, jr.id ORDER BY jr.rating DESC)")
	public List<JokeRating> listTopRankings(@Param("category") String category, Pageable pageable);
	
	@Query(value= "SELECT j FROM JokeRating j GROUP BY j.jokeId ORDER BY j.rating DESC")
	public List<JokeRating> listTopRankings(Pageable pageable);
}
