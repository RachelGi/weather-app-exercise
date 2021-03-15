package com.climacell.weather_app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.climacell.weather_app.configuration.BatchConfiguration;
import com.climacell.weather_app.exception.NoDataFoundException;
import com.climacell.weather_app.model.Weather;
import com.climacell.weather_app.model.WeatherAtLocation;
import com.climacell.weather_app.model.WeatherSummarize;
import com.climacell.weather_app.service.WeatherService;

@RestController
public class WeatherForecastController {
	//TODO addon log, test

	@Autowired
	private WeatherService weatherService;

	/**
	 * returns the weather forecast in a specific location for a specific time
	 * send {@link HttpStatus.BAD_REQUEST} for a wrong location and 
	 * {@link HttpStatus.BAD_REQUEST} if no data was found for the given location
	 * @param latitude needs to apply the location rule - see {@link Weather.isValideLocation}
	 * @param longitude  needs to apply the location rule - see {@link Weather.isValideLocation}
	 * @param response 
	 * @return the weather forecast in a specific location for a specific time
	 * @throws IOException
	 */
	@GetMapping("/weather/data") 
	private List<WeatherAtLocation> retrieveWeatherForecastByLocation(@RequestParam Double latitude, @RequestParam Double longitude, HttpServletResponse response ) throws IOException { 
		if(!Weather.isValideLocation(latitude, longitude)) {
			response.sendError(HttpStatus.BAD_REQUEST.value(), "Location "+ longitude + " " +  latitude + " not existing"); 
			return null;
		}
		List<WeatherAtLocation> weatherList = null; 
		try {
			weatherList = weatherService.retrieveWeathersAtLocation(longitude, latitude);
		} catch (NoDataFoundException e) {
			response.sendError(HttpStatus.NOT_FOUND.value(), "No Weather forcast for location "+ + longitude + " " + latitude); //TODO check if NO_CONTENT
		}
		return weatherList;
	}

	
	/**
	 * returns the max,min,avg weather data (from all the files) for a specific locatio
	 * send {@link HttpStatus.BAD_REQUEST} for a wrong location and 
	 * {@link HttpStatus.BAD_REQUEST} if no data was found for the given location
	 * @param latitude needs to apply the location rule - see {@link Weather.isValideLocation}
	 * @param longitude  needs to apply the location rule - see {@link Weather.isValideLocation}
	 * @param response 
	 * @return the weather summarize in a specific location
	 * @throws IOException
	 */
	
	@GetMapping("/weather/summarize") 
	private HashMap<SummarizeField,WeatherSummarize> retrieveWeatherForecastSummarizeByLocation(@RequestParam Double latitude, @RequestParam Double longitude,HttpServletResponse response) 
			throws IOException{
		HashMap<SummarizeField,WeatherSummarize> summarizeWeather = null; 
		if(!Weather.isValideLocation(latitude, longitude)) {
			response.sendError(HttpStatus.BAD_REQUEST.value(), "Location "+ longitude + " " +  latitude + " not existing"); 
			return null;
		}
		try {
			summarizeWeather = weatherService.retrieveSummarizeWeathersAtLocation(longitude, latitude);
		} catch (NoDataFoundException e) {
			response.sendError(HttpStatus.NOT_FOUND.value(), "No Weather forcast for location "+ longitude + " " +  latitude); 
		}
		return summarizeWeather;
	}

	

}
