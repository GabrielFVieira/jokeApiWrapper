package com.gabrielfigueiredo.jokeApiWrapper.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielfigueiredo.jokeApiWrapper.dto.JokeDTO;
import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;
import com.gabrielfigueiredo.jokeApiWrapper.model.enums.JokeType;
import com.gabrielfigueiredo.jokeApiWrapper.service.JokeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("joke")
@RequiredArgsConstructor
public class JokeController {
	private final JokeService jokeService;
	
	@GetMapping()
	public JokeDTO getJoke(@RequestParam(required = false) String type, @RequestParam(required = false) String[] categories) {
		Joke joke = jokeService.getJoke(JokeType.fromText(type), categories);
		
		return new JokeDTO(joke);
	}
	
	@GetMapping("/test")
	public Boolean testConnection() {
		return jokeService.testConnection();
	}
}
