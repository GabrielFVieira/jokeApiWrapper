package com.gabrielfigueiredo.jokeApiWrapper.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielfigueiredo.jokeApiWrapper.dto.JokeDTO;
import com.gabrielfigueiredo.jokeApiWrapper.dto.SummaryDTO;
import com.gabrielfigueiredo.jokeApiWrapper.dto.UnratedJokesDTO;
import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;
import com.gabrielfigueiredo.jokeApiWrapper.service.CategoryService;
import com.gabrielfigueiredo.jokeApiWrapper.service.JokeApiService;
import com.gabrielfigueiredo.jokeApiWrapper.service.JokeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("summary")
@RequiredArgsConstructor
public class SummaryController {
	private final CategoryService categoryService;
	private final JokeService jokeService;
	
	@GetMapping()
	public SummaryDTO getSummary(@RequestParam(defaultValue = JokeApiService.DEFAULT_LANGUAGE) String lang, Pageable pageable) {
		List<String> categories = categoryService.list();
		SummaryDTO sumary = new SummaryDTO();
		sumary.setLang(lang);
		
		categories.forEach(category -> {
			if(!JokeApiService.DEFAULT_CATEGORY.equals(category.toLowerCase())) {
				List<Joke> topJokes = jokeService.getTopJokes(category, lang);
				List<JokeDTO> jokesDTO = topJokes.stream().map(joke -> new JokeDTO(joke)).collect(Collectors.toList());
				sumary.addTopJoke(category, jokesDTO);
			}
		});
		
		sumary.setUnratedSeenJokes(getUnratedJokes(lang, pageable));
		
		return sumary;
	}
	
	@GetMapping("/unrated")
	public UnratedJokesDTO getUnratedJokes(@RequestParam(defaultValue = JokeApiService.DEFAULT_LANGUAGE) String lang, Pageable pageable) {
		Page<Joke> jokes = jokeService.getUnratedJokes(lang, pageable);
		List<JokeDTO> jokesDTO = jokes.stream().map(joke -> new JokeDTO(joke)).collect(Collectors.toList());
		return new UnratedJokesDTO(jokes.getNumber(), jokes.getTotalPages() - 1, jokes.getSize(), jokesDTO, lang);
	}
}
