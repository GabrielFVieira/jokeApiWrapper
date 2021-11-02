package com.gabrielfigueiredo.jokeApiWrapper.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gabrielfigueiredo.jokeApiWrapper.dto.JokeDTO;
import com.gabrielfigueiredo.jokeApiWrapper.dto.JokeRatingDTO;
import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;
import com.gabrielfigueiredo.jokeApiWrapper.model.JokeRating;

@Configuration
public class ModelMappingConfiguration {
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new PropertyMap <Joke, JokeDTO>() {
		      protected void configure() {
		          map().setId(source.getJokeApiId());
		      }
		  }
		);
		modelMapper.addMappings(new PropertyMap <JokeRating, JokeRatingDTO>() {
		      protected void configure() {
		          map().setJokeId(source.getJoke().getJokeApiId());
		          map().setLang(source.getJoke().getLang());
		      }
		  }
		);
		
	    return modelMapper;
	}
}
