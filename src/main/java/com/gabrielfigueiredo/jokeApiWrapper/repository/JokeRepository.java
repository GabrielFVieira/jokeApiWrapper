package com.gabrielfigueiredo.jokeApiWrapper.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;

public interface JokeRepository extends JpaRepository<Joke, Integer> {

	@Query(value= "SELECT j.* FROM joke j "
			+ "INNER JOIN "
			+ "(SELECT jr.joke_id as jid, AVG(jr.rating) as ratingAvg "
			+ "FROM joke_rating jr "
			+ "GROUP BY jr.joke_id) as ranking "
			+ "ON ranking.jid = j.id "
			+ "WHERE j.category = :category "
			+ "AND j.lang = :lang "
			+ "ORDER BY ranking.ratingAvg DESC", nativeQuery = true)
	public List<Joke> listTopJokes(@Param("category") String category, @Param("lang") String language, Pageable pageable);
	
	@Query(value= "SELECT j.* FROM joke j "
				+ "INNER JOIN "
				+ "(SELECT jr.joke_id as jid, AVG(jr.rating) as ratingAvg "
				+ "FROM joke_rating jr "
				+ "GROUP BY jr.joke_id) as ranking "
				+ "ON ranking.jid = j.id "
				+ "WHERE j.lang = :lang "
				+ "ORDER BY ranking.ratingAvg DESC", nativeQuery = true)
public List<Joke> listTopJokes(@Param("lang") String language, Pageable pageable);
	
	@Query(value= "SELECT j FROM Joke j "
				+ "WHERE j.lang = :lang "
				+ "AND j NOT IN (SELECT jr.joke FROM JokeRating jr)")
	public Page<Joke> listUnratedJokes(@Param("lang") String language, Pageable pageable);
}
