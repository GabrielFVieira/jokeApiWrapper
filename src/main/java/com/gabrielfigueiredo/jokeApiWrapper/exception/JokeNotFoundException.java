package com.gabrielfigueiredo.jokeApiWrapper.exception;

import lombok.Getter;

public class JokeNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	@Getter
	private String aditionalInfo;
	
	public JokeNotFoundException(String message) {
		super(message);
	}
	
	public JokeNotFoundException(String message, String aditionalInfo) {
		super(message);
		this.aditionalInfo = aditionalInfo;
	}
}
