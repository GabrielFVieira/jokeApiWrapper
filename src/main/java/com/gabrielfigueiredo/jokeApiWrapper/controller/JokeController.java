package com.gabrielfigueiredo.jokeApiWrapper.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
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
import com.gabrielfigueiredo.jokeApiWrapper.service.JokeApiService;
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
	public JokeDTO getJoke(@RequestParam Optional<String> type, 
			 			   @RequestParam(defaultValue = JokeApiService.DEFAULT_LANGUAGE) String lang,
						   @RequestParam(defaultValue = JokeApiService.DEFAULT_CATEGORY) String... categories) {
		
		Joke joke = jokeService.getJoke(type, lang, categories);
		return new JokeDTO(joke);
	}
	
	@GetMapping("/{id}")
	public JokeDTO getJokeById(@PathVariable Integer id, @RequestParam(defaultValue = JokeApiService.DEFAULT_LANGUAGE) String lang) {
		Joke joke = jokeService.find(id, lang);
		return new JokeDTO(joke);
	}
	
	@PostMapping("/{id}/rate")
	public void rateJoke(@PathVariable Integer id, @RequestBody JokeRatingDTO rating) {
		String language = Strings.isEmpty(rating.getLang()) ? JokeApiService.DEFAULT_LANGUAGE : rating.getLang();
		
		jokeRatingService.createRating(id, language, rating.getRating(), rating.getCommentary());
	}
	
	@GetMapping("/{id}/rate")
	public List<JokeRatingDTO> getRatings(@PathVariable Integer id,
										  @RequestParam(defaultValue = JokeApiService.DEFAULT_LANGUAGE) String lang) {
		
		List<JokeRating> ratings = jokeRatingService.list(id, lang);
		
		List<JokeRatingDTO> ratingsDTO = ratings.stream()
												.map(rating -> new JokeRatingDTO(rating))
												.collect(Collectors.toList());
		
		return ratingsDTO;
	}
	
	@GetMapping("/{category}/top")
	public List<JokeDTO> getTopJokes(@PathVariable String category, 
									 @RequestParam(defaultValue = JokeApiService.DEFAULT_LANGUAGE) String lang, 
									 @RequestParam(defaultValue = JokeApiService.DEFAULT_TOP_AMOUNT) Integer amount) {
		
		List<Joke> jokes = jokeService.getTopJokes(category, lang, amount);
		
		List<JokeDTO> jokesDTO = jokes.stream()
									  .map(joke -> new JokeDTO(joke))
									  .collect(Collectors.toList());
		
		return jokesDTO;
	}
}
