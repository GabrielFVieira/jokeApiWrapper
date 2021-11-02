package com.gabrielfigueiredo.jokeApiWrapper.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.gabrielfigueiredo.jokeApiWrapper.exception.ServerException;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	private final WebClient jokeApiClient;
	
	@Override
	public List<String> list() {
		try {
			CategoriesResponse categories = this.jokeApiClient
					.get()
					.uri("/categories")
					.retrieve()
					.bodyToMono(CategoriesResponse.class)
					.block();
			
			if(categories.hasError()) {
				throw new ServerException(categories.getMessage());
			}
			
			return categories.getCategories();
		} catch (ServerException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException("Error while fetching the categories");
		}
	}
	
	@Data
	static class CategoriesResponse {
		private List<String> categories;
		
		@Getter(AccessLevel.NONE)
		private Boolean error;
		private String message;
		
		public Boolean hasError() {
			return error;
		}
	}
}


