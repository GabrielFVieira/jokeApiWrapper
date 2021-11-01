package com.gabrielfigueiredo.jokeApiWrapper.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SummaryDTO {
	private Map<String, List<JokeDTO>> topJokes;
	private UnratedJokesDTO unratedSeenJokes;
	private String lang;
	
	public void addTopJoke(String category, List<JokeDTO> jokes) {
		if(this.topJokes == null) {
			this.topJokes = new HashMap<String, List<JokeDTO>>();
		}
		
		this.topJokes.put(category, jokes);
	}
}
