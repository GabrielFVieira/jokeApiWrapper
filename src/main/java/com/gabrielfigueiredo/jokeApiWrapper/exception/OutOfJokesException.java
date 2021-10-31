package com.gabrielfigueiredo.jokeApiWrapper.exception;

public class OutOfJokesException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "You're out of jokes";
	}
}