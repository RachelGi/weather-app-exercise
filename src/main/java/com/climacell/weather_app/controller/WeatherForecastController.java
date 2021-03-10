package com.climacell.weather_app.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.climacell.weather_app.model.Weather;

@RestController
public class WeatherForecastController {

	@GetMapping("/weather/data")
	private String printWelcom( ) {
		
		
		return "welcome";
	}
	
	
	@GetMapping("/weather/data/{lat}/{lon}") //TODO use @RequestParams
	private List<Weather> retrieveWeatherForecastByLocation(@PathVariable double lat, @PathVariable double lon ) {
		
		
		return null;
	}
	
	@GetMapping("/weather/summarize/{lat}/{lon}") //TODO use @RequestParams
	private HashMap<StatisticField,Weather> retrieveWeatherForecastSummarizeByLocation(@PathVariable double lat, @PathVariable double lon ) {
		
		
		return null;
	}
	
}
