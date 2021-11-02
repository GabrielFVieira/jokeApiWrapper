package com.gabrielfigueiredo.jokeApiWrapper.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JokeRatingDTO {
	private Integer jokeId;
	private String lang;
	private Integer rating;
	private String commentary;
	private Date date;
}
