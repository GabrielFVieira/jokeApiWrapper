package com.gabrielfigueiredo.jokeApiWrapper.dto;

import java.util.Date;

import com.gabrielfigueiredo.jokeApiWrapper.model.JokeRating;

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
	
	public JokeRatingDTO(JokeRating entity) {
		this.jokeId = entity.getJoke().getId();
		this.lang = entity.getJoke().getLang();
		this.rating = entity.getRating();
		this.commentary = entity.getCommentary();
		this.date = entity.getDate();
	}
}
