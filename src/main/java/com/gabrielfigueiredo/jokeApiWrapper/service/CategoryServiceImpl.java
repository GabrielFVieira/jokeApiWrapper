package com.gabrielfigueiredo.jokeApiWrapper.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gabrielfigueiredo.jokeApiWrapper.exception.ServerException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	private final JokeApiService apiService;
	
	@Override
	public List<String> list() {
		try {
			return apiService.listCategories();
		} catch (ServerException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException("Error while fetching the categories");
		}
	}
}


