package com.climacell.weather_app.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.climacell.weather_app.model.Location;
import com.climacell.weather_app.model.Weather;
import com.climacell.weather_app.model.WeatherTemp;
import com.climacell.weather_app.repository.LocationRepository;
import com.climacell.weather_app.repository.WeatherRepository;
import com.climacell.weather_app.repository.WeatherTempRepository;

@RestController
public class WeatherForecastController {
//addon log, test

	@Autowired
	private WeatherRepository weatherRepository;
	@Autowired
	private WeatherTempRepository weatherTempRepository;
	@Autowired
	private LocationRepository locationRepository;


	@GetMapping("/")
	private String printWelcome( ) {
		Weather weather =  new Weather();
		Location loc = new Location();
		loc.setLatitude(2.0);
		loc.setLongitude(3.4);
		locationRepository.save(loc);
		weather.setLocation(loc);
		weather.setTemperature(2.54);
		weatherRepository.save(weather );

		return "welcome";
	}

	@GetMapping("/test")
	private String createTableTest( ) {
		WeatherTemp weather =  new WeatherTemp();
		weather.setLatitude(2.3);
		weather.setLongitude(3.4);
		weather.setTemperature(2.54);
		weatherTempRepository.save(weather );

		return "welcome";
	}

	
	@GetMapping("/weathers")
	private List<Weather> printAllWeather( ) {

		return weatherRepository.findAll( );
	}

	@GetMapping("/weathersTemp")
	private List<WeatherTemp> printAllWeatherTemp( ) {

		return weatherTempRepository.findAll( );
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
