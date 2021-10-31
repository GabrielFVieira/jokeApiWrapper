package com.gabrielfigueiredo.jokeApiWrapper.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class JokeHistory {
	@Id
	@GeneratedValue
	private Integer id;
	
	@NonNull
	private Integer jokeId;
	
	@NonNull
	private String language;
	
	@CreationTimestamp
	private Date data;
	
	public JokeHistory(Joke joke) {
		this.jokeId = joke.getId();
		this.language = joke.getLang();
	}
}
