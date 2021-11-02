package com.gabrielfigueiredo.jokeApiWrapper.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class JokeRating {
	public static final long MAX_RATING = 5;
	public static final long MIN_RATING = 1;
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private Joke joke;
	
	@NonNull
	@Max(value=MAX_RATING, message="The rating should not be greater than " + MAX_RATING)
	@Min(value=MIN_RATING, message="The rating should not be less than " + MIN_RATING)
	private Integer rating;
	
	@Nullable
	private String commentary ;
	
	@CreationTimestamp
	private Date date;
	
	public JokeRating(Joke joke, Integer rating, String commentary) {
		this.joke = joke;
		this.rating = rating;
		this.commentary = commentary;
	}
}
