package com.gabrielfigueiredo.jokeApiWrapper.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum JokeType {
	SINGLE, TWOPART;

	@JsonCreator
	public static JokeType fromText(String text) {
		try {
			return JokeType.valueOf(text.toUpperCase());
		} catch (Exception e) {
			return null;
		}
	}
}
