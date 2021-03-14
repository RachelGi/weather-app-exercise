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

import com.climacell.weather_app.exception.NoDataFoundException;
import com.climacell.weather_app.model.Location;
import com.climacell.weather_app.model.Weather;
import com.climacell.weather_app.model.WeatherSummarize;
import com.climacell.weather_app.repository.WeatherRepository;
import com.climacell.weather_app.service.WeatherService;

@RestController
public class WeatherForecastController {
	//TODO addon log, test

	@Autowired
	private WeatherRepository weatherRepository; //TODO remove it
	@Autowired
	private WeatherService weatherService;

	@GetMapping("/")
	private String printWelcome( ) {
		Weather weather =  new Weather();
		weather.setLatitude(2.3);
		weather.setLongitude(3.4);
		weather.setTemperature(2.54);
		weatherRepository.save(weather );
		return "welcome";
	}


	@GetMapping("/weathers")
	private List<Weather> printAllWeather( ) throws Exception{
		weatherService.importWeatherDataFromCSVFile();
		return weatherRepository.findAll( );
	}


	@GetMapping("/weather/data")  //TODO use Location
	private List<Weather> retrieveWeatherForecastByLocation(@RequestParam Double latitude, @RequestParam Double longitude, HttpServletResponse response ) throws IOException { //@PathVariable double lat, @PathVariable double lon,
		List<Weather> weatherList = null; 
		try {
			weatherList = weatherService.retrieveWeathersAtLocation(longitude, latitude);
		} catch (NoDataFoundException e) {
			response.sendError(HttpStatus.NOT_FOUND.value(), "No Weather forcast for location "+ + longitude + " " + latitude); //TODO check if NO_CONTENT
		}
		return weatherList;
	}

	@GetMapping("/weather/summarize") //TODO use Location
	private HashMap<StatisticField,WeatherSummarize> retrieveWeatherForecastSummarizeByLocation(@RequestParam Double latitude, @RequestParam Double longitude,HttpServletResponse response) throws IOException{
		HashMap<StatisticField,WeatherSummarize> summarizeWeather = null; 
		try {
			summarizeWeather = weatherService.retrieveSummarizeWeathersAtLocation(longitude, latitude);
		} catch (NoDataFoundException e) {
			response.sendError(HttpStatus.NOT_FOUND.value(), "No Weather forcast for location "+ longitude + " " +  latitude); //TODO check if NO_CONTENT
		}
		return summarizeWeather;
	}

}
