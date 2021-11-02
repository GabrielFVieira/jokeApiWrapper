package com.gabrielfigueiredo.jokeApiWrapper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.gabrielfigueiredo.jokeApiWrapper.config.ModelMappingConfiguration;
import com.gabrielfigueiredo.jokeApiWrapper.dto.JokeDTO;
import com.gabrielfigueiredo.jokeApiWrapper.dto.JokeRatingDTO;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ModelMappingConfiguration.class)
@SpringBootTest
public class EntityToDtoTest {
	@Autowired
	private ModelMapper modelMapper;
	
	@Test
	public void testJokeEntityToJokeDtoConversion() {
		Joke joke = new Joke();
		joke.setId(1);
		joke.setJokeApiId(2);
		joke.setJoke("Debugging: Removing the needles from the haystack.");
		
		JokeDTO jokeDto = modelMapper.map(joke, JokeDTO.class);
		
		assertEquals(joke.getJokeApiId(), jokeDto.getId());
		assertEquals(joke.getJoke(), jokeDto.getJoke());
		assertNotEquals(joke.getId(), jokeDto.getId());
	}
	
	@Test
	public void testJokeRatingEntityToJokeDtoConversion() {
		Joke joke = new Joke();
		joke.setJokeApiId(1);
		joke.setLang("en");
		
		JokeRating jokeRating = new JokeRating();
		jokeRating.setJoke(joke);
		jokeRating.setRating(5);
		jokeRating.setCommentary("So much fun here");
		jokeRating.setDate(new Date());
		
		JokeRatingDTO jokeRatingDto = modelMapper.map(jokeRating, JokeRatingDTO.class);
		
		assertEquals(joke.getJokeApiId(), jokeRatingDto.getJokeId());
		assertEquals(joke.getLang(), jokeRatingDto.getLang());
		assertEquals(jokeRating.getRating(), jokeRatingDto.getRating());
		assertEquals(jokeRating.getCommentary(), jokeRatingDto.getCommentary());
		assertEquals(jokeRating.getDate(), jokeRatingDto.getDate());
	}
}
