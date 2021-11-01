package com.gabrielfigueiredo.jokeApiWrapper.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="joke", uniqueConstraints = @UniqueConstraint(columnNames = {"jokeApiId", "lang"}))
@NoArgsConstructor
public class Joke {
	@Id
	@GeneratedValue
	private Integer id;
	
	@NonNull
	@Column(name = "jokeApiId")
	private Integer jokeApiId;
	
	@NonNull
	private String lang;
	
	@NonNull
	@Lob
	private String joke;
	
	@NonNull
	private String category;

	@CreationTimestamp
	@Column(name = "viewingDate")
	private Date viewingDate;
}