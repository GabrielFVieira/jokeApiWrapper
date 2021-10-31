package com.gabrielfigueiredo.jokeApiWrapper.model;

import com.gabrielfigueiredo.jokeApiWrapper.model.enums.JokeType;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
public class Joke {
	private Integer id;
	private String category;
	private JokeType type;
	private String joke;
	private String setup;
	private String delivery;
	private Boolean safe;
	private String lang;
	
	@Getter(AccessLevel.NONE)
	private Boolean error;
	private String message;
	private String additionalInfo;
	
	public String getCompleteJoke() {
		if(JokeType.TWOPART.equals(type)) {
			return addDash(setup) + "\n " + addDash(delivery);
		} else {
			return joke;
		}
	}
	
	private String addDash(String sentence) {
		return "- " + sentence;
	}
	
	public Boolean hasError() {
		return error;
	}
}
