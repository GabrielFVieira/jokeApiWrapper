package com.gabrielfigueiredo.jokeApiWrapper.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.gabrielfigueiredo.jokeApiWrapper.dto.JokeDTO;
import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;
import com.gabrielfigueiredo.jokeApiWrapper.service.CategoryService;
import com.gabrielfigueiredo.jokeApiWrapper.service.JokeApiService;
import com.gabrielfigueiredo.jokeApiWrapper.service.JokeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("summary")
@RequiredArgsConstructor
public class SummaryController {
	private final CategoryService categoryService;
	private final JokeService jokeService;
	private final ModelMapper modelMapper;
	
	private static final String JOKE_LANGUAGE = "The language of the jokes";
	
	@ApiOperation("Get a summary")
	@GetMapping()
	public SummaryResponse getSummary(@ApiParam(value = JOKE_LANGUAGE)
									  @RequestParam(defaultValue = JokeApiService.DEFAULT_LANGUAGE) String lang, 
									  Pageable pageable) {
		
		List<String> categories = categoryService.list();
		SummaryResponse sumary = new SummaryResponse();
		sumary.setLang(lang);
		
		categories.forEach(category -> {
			if(!JokeApiService.DEFAULT_CATEGORY.equals(category.toLowerCase())) {
				List<Joke> topJokes = jokeService.getTopJokes(category, lang);
				List<JokeDTO> jokesDTO = topJokes.stream().map(joke -> convertToDto(joke)).collect(Collectors.toList());
				sumary.addTopJoke(category, jokesDTO);
			}
		});
		
		sumary.setUnratedSeenJokes(getUnratedJokes(lang, pageable));
		
		return sumary;
	}
	
	@ApiOperation("Get only the unrated seen jokes")
	@GetMapping("/unrated")
	public UnratedJokesResponse getUnratedJokes(@ApiParam(value = JOKE_LANGUAGE)
												@RequestParam(defaultValue = JokeApiService.DEFAULT_LANGUAGE) String lang, 
												Pageable pageable) {
		
		Page<Joke> jokes = jokeService.getUnratedJokes(lang, pageable);
		List<JokeDTO> jokesDTO = jokes.stream().map(joke -> convertToDto(joke)).collect(Collectors.toList());
		
		 // Removing 1 because the pages starts at 0 index
		Integer totalPages = jokes.getTotalPages() > 0 ? jokes.getTotalPages() - 1 : 0;
		return new UnratedJokesResponse(jokes.getNumber(), totalPages, jokes.getSize(), jokesDTO, lang);
	}
	
	private JokeDTO convertToDto(Joke joke) {
		return modelMapper.map(joke, JokeDTO.class);
	}
	
	@Data
	class SummaryResponse {
		private Map<String, List<JokeDTO>> topJokes;
		private UnratedJokesResponse unratedSeenJokes;
		private String lang;
		
		public void addTopJoke(String category, List<JokeDTO> jokes) {
			if(this.topJokes == null) {
				this.topJokes = new HashMap<String, List<JokeDTO>>();
			}
			
			this.topJokes.put(category, jokes);
		}
	}

	@Data
	@JsonInclude(Include.NON_NULL)
	class UnratedJokesResponse {
		private Integer curPage;
		private Integer lastPage;
		private Integer pageSize;
		private List<JokeDTO> jokes;
		private String lang;
		
		public UnratedJokesResponse(Integer curPage, Integer lastPage, Integer pageSize, List<JokeDTO> jokes, String lang) {
			this.curPage = curPage;
			this.lastPage = lastPage;
			this.pageSize = pageSize;
			this.jokes = jokes;
			this.lang = lang;
		}
	}
}




