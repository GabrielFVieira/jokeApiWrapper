package com.gabrielfigueiredo.jokeApiWrapper.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class UnratedJokesDTO {
	private Integer curPage;
	private Integer lastPage;
	private Integer pageSize;
	private List<JokeDTO> jokes;
	private String lang;
	
	public UnratedJokesDTO(Integer curPage, Integer lastPage, Integer pageSize, List<JokeDTO> jokes, String lang) {
		this.curPage = curPage;
		this.lastPage = lastPage;
		this.pageSize = pageSize;
		this.jokes = jokes;
		this.lang = lang;
	}
}
