package com.gabrielfigueiredo.jokeApiWrapper.dto;

import java.util.Date;

import com.gabrielfigueiredo.jokeApiWrapper.model.JokeRating;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JokeRatingDTO { // Class that store the data returned by the endpoints
	private Integer id;
	private Integer rating;
	private String commentary;
	private Date date;
	
	public JokeRatingDTO(JokeRating entity) {
		this.id = entity.getId();
		this.rating = entity.getRating();
		this.commentary = entity.getCommentary();
		this.date = entity.getDate();
	}
}
