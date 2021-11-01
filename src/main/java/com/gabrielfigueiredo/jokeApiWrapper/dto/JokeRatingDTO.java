package com.gabrielfigueiredo.jokeApiWrapper.dto;

import java.util.Date;

import com.gabrielfigueiredo.jokeApiWrapper.model.JokeRating;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JokeRatingDTO {
	private Integer id;
	private String lang;
	private Integer rating;
	private String commentary;
	private Date date;
	
	public JokeRatingDTO(JokeRating entity) {
		this.id = entity.getId();
		this.lang = entity.getLanguage();
		this.rating = entity.getRating();
		this.commentary = entity.getCommentary();
		this.date = entity.getDate();
	}
}
