package com.gabrielfigueiredo.jokeApiWrapper.service;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
public class JokeResponse {
	private Integer id;
	private String category;
	private String type;
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
		if("twopart".equals(type.toLowerCase())) {
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