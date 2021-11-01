package com.gabrielfigueiredo.jokeApiWrapper.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class JokeRating { // Class that represent the joke_rating table
	@Id
	@GeneratedValue
	private Integer id;
	
	@NonNull
	private Integer jokeId;
	
	@NonNull
	private String language;
	
	@NonNull
	private String category;
	
	@NonNull
	private Integer rating;
	
	@Nullable
	private String commentary ;
	
	@CreationTimestamp
	private Date date;
	
	public JokeRating(Integer jokeId, String language, String category, Integer rating, String commentary) {
		this.jokeId = jokeId;
		this.language = language;
		this.category = category;
		this.rating = rating;
		this.commentary = commentary;
	}
}
