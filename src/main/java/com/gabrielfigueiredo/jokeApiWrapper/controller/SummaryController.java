package com.gabrielfigueiredo.jokeApiWrapper.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielfigueiredo.jokeApiWrapper.dto.JokeDTO;
import com.gabrielfigueiredo.jokeApiWrapper.dto.SummaryDTO;
import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;
import com.gabrielfigueiredo.jokeApiWrapper.service.CategoryService;
import com.gabrielfigueiredo.jokeApiWrapper.service.JokeHistoryService;
import com.gabrielfigueiredo.jokeApiWrapper.service.JokeService;
import com.gabrielfigueiredo.jokeApiWrapper.util.JokeApiUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("summary")
@RequiredArgsConstructor
public class SummaryController {
	private final CategoryService categoryService;
	private final JokeService jokeService;
	private final JokeHistoryService jokeHistoryService;
	
	@GetMapping()
	public SummaryDTO getSummary(@RequestParam(defaultValue = JokeApiUtils.DEFAULT_LANGUAGE) String lang) {
		List<String> categories = categoryService.list();
		SummaryDTO sumary = new SummaryDTO();
		sumary.setLang(lang);
		
		categories.forEach(category -> {
			if(!JokeApiUtils.DEFAULT_CATEGORY.equals(category.toLowerCase())) {
				List<Joke> topJokes = jokeService.getTopJokes(category, lang);
				List<JokeDTO> jokesDTO = topJokes.stream().map(joke -> new JokeDTO(joke)).collect(Collectors.toList());
				sumary.addTopJoke(category, jokesDTO);
			}
		});
		
		sumary.setUnratedSeenJokes(jokeHistoryService.getAllUnratedJokes(lang));
		
		return sumary;
	}
}
