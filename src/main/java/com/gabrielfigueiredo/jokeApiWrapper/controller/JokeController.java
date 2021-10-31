package com.gabrielfigueiredo.jokeApiWrapper.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielfigueiredo.jokeApiWrapper.dto.JokeDTO;
import com.gabrielfigueiredo.jokeApiWrapper.dto.JokeRatingDTO;
import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;
import com.gabrielfigueiredo.jokeApiWrapper.model.JokeRating;
import com.gabrielfigueiredo.jokeApiWrapper.model.enums.JokeType;
import com.gabrielfigueiredo.jokeApiWrapper.service.JokeRatingService;
import com.gabrielfigueiredo.jokeApiWrapper.service.JokeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("joke")
@RequiredArgsConstructor
public class JokeController {
	private final JokeService jokeService;
	private final JokeRatingService jokeRatingService;
	
	@GetMapping()
	public JokeDTO getJoke(@RequestParam(required = false) String type, @RequestParam(required = false) String[] categories) {
		Joke joke = jokeService.getJoke(JokeType.fromText(type), categories);
		
		return new JokeDTO(joke);
	}
	
	@PostMapping("/{id}/rate")
	public void rateJoke(@PathVariable Integer id, @RequestBody JokeRatingDTO rating) {
		jokeRatingService.createRating(id, rating.getRating(), rating.getCommentary());
	}
	
	@GetMapping("/{id}/rate")
	public List<JokeRatingDTO> getRatings(@PathVariable Integer id) {
		List<JokeRating> ratings = jokeRatingService.list(id);
		List<JokeRatingDTO> ratingsDTO = new ArrayList<>();
		ratings.forEach(rating -> ratingsDTO.add(new JokeRatingDTO(rating)));
		
		return ratingsDTO;
	}
	
	@GetMapping("/{category}/top")
	public List<JokeDTO> getTopJokes(@PathVariable String category, @RequestParam(required = false) Integer amount) {
		List<Joke> jokes = jokeService.getTopJokes(category, amount);
		List<JokeDTO> jokesDTO = new ArrayList<>();
		jokes.forEach(joke -> jokesDTO.add(new JokeDTO(joke)));
		
		return jokesDTO;
	}
}
