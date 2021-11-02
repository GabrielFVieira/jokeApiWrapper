package com.gabrielfigueiredo.jokeApiWrapper.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JokeDTO {
	private Integer id;
	private String joke;
}
