package com.gabrielfigueiredo.jokeApiWrapper.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class JokeRating {
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private Joke joke;
	
	@NonNull
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
