package com.gabrielfigueiredo.jokeApiWrapper.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielfigueiredo.jokeApiWrapper.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryController {
	private final CategoryService service;
	
	@GetMapping()
	public List<String> getCategories() {
		return service.list();
	}
}
