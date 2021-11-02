package com.gabrielfigueiredo.jokeApiWrapper.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielfigueiredo.jokeApiWrapper.dto.JokeDTO;
import com.gabrielfigueiredo.jokeApiWrapper.dto.JokeRatingDTO;
import com.gabrielfigueiredo.jokeApiWrapper.exception.ServerException;
import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;
import com.gabrielfigueiredo.jokeApiWrapper.model.JokeRating;
import com.gabrielfigueiredo.jokeApiWrapper.service.JokeApiService;
import com.gabrielfigueiredo.jokeApiWrapper.service.JokeRatingService;
import com.gabrielfigueiredo.jokeApiWrapper.service.JokeService;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("joke")
@RequiredArgsConstructor
public class JokeController {
	private final JokeService jokeService;
	private final JokeRatingService jokeRatingService;
	private final ModelMapper modelMapper;

	private static final String RATING_RANGE = "(min: " + JokeRating.MIN_RATING + ", max: " + JokeRating.MAX_RATING + ")";
	private static final String JOKE_ID = "The joke id";
	private static final String JOKE_LANGUAGE = "The joke language";
	
	@ApiOperation("Get a joke")
	@GetMapping()
	public JokeDTO getJoke(@ApiParam(value = "The joke type, could be either 'single' or 'twopart'",  example = "single") 
						   @RequestParam(required = false) Optional<String> type, 
						   @ApiParam(value = JOKE_LANGUAGE) @RequestParam(defaultValue = JokeApiService.DEFAULT_LANGUAGE) String lang,
						   @ApiParam(value = "A array of the joke categories") 
						   @RequestParam(required = false) String... categories) {
		
		if(categories == null) { // Changed to this way to prevent error in Swagger UI example of the categories field
			categories = new String[]{JokeApiService.DEFAULT_CATEGORY};
		}
		
		return convertToDto(jokeService.getJoke(type, lang, categories));
	}
	
	@ApiOperation("Fetch a seen joke by it's id")
	@GetMapping("/{id}")
	public JokeDTO getJokeById(@ApiParam(value = JOKE_ID,  example = "21") @PathVariable Integer id, 
							   @ApiParam(value = JOKE_LANGUAGE) @RequestParam(defaultValue = JokeApiService.DEFAULT_LANGUAGE) String lang) {
		return convertToDto(jokeService.find(id, lang));
	}
	
	@ApiOperation("Rate a seen joke")
	@PostMapping("/{id}/rate")
	public void rateJoke(@ApiParam(value = JOKE_ID,  example = "5") @PathVariable Integer id, 
						 @RequestBody JokeRatingRequest rating) {
		String language = Strings.isEmpty(rating.getLang()) ? JokeApiService.DEFAULT_LANGUAGE : rating.getLang();
		
		if(rating.getRating() == null || rating.getRating() > JokeRating.MAX_RATING ||
		   rating.getRating() < JokeRating.MIN_RATING) {
			throw new ServerException("Rating out of range " + RATING_RANGE);
		}
		
		jokeRatingService.createRating(id, language, rating.getRating(), rating.getCommentary());
	}
	
	@ApiOperation("Get all rates of a seen joke")
	@GetMapping("/{id}/rate")
	public List<JokeRatingDTO> getRatings(@ApiParam(value = JOKE_ID,  example = "223") @PathVariable Integer id,
										  @ApiParam(value = JOKE_LANGUAGE) 
										  @RequestParam(defaultValue = JokeApiService.DEFAULT_LANGUAGE) String lang) {
		
		List<JokeRating> ratings = jokeRatingService.list(id, lang);
		
		List<JokeRatingDTO> ratingsDTO = ratings.stream()
												.map(rating -> convertToDto(rating))
												.collect(Collectors.toList());
		
		return ratingsDTO;
	}
	
	@ApiOperation("Get the top jokes of a category")
	@GetMapping("/{category}/top")
	public List<JokeDTO> getTopJokes(@ApiParam(value = "The joke category", example = JokeApiService.DEFAULT_CATEGORY) 
									 @PathVariable String category, 
									 @ApiParam(value = JOKE_LANGUAGE) 
									 @RequestParam(defaultValue = JokeApiService.DEFAULT_LANGUAGE) String lang, 
									 @ApiParam(value = "The amount of jokes to be returned") 
									 @RequestParam(defaultValue = JokeApiService.DEFAULT_TOP_AMOUNT) Integer amount) {
		
		List<Joke> jokes = jokeService.getTopJokes(category, lang, amount);
		
		List<JokeDTO> jokesDTO = jokes.stream()
									  .map(joke -> convertToDto(joke))
									  .collect(Collectors.toList());
		
		return jokesDTO;
	}
	
	private JokeDTO convertToDto(Joke joke) {
		return modelMapper.map(joke, JokeDTO.class);
	}
	
	private JokeRatingDTO convertToDto(JokeRating jokeRating) {
		return modelMapper.map(jokeRating, JokeRatingDTO.class);
	}
	
	@Data
	static class JokeRatingRequest {
		@ApiModelProperty(value = "Language (if none is passed, the default is 'en')", required = false)
		private String lang;
		@ApiModelProperty(value = "The joke rating " + RATING_RANGE)
		private Integer rating;
		@ApiModelProperty(value = "Some commentary about the joke", required = false)
		private String commentary;
	}
}
