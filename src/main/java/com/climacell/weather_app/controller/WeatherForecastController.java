package com.climacell.weather_app.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.climacell.weather_app.model.Weather;
import com.climacell.weather_app.repository.WeatherRepository;

@RestController
public class WeatherForecastController {


	@Autowired
	private WeatherRepository weatherRepository;


	@GetMapping("/")
	private String printWelcome( ) {
		Weather weather =  new Weather();
		weather.setLatitude(3.0);
		weather.setLongitude(2.0);
		weather.setTemperature(2.54);
		weatherRepository.save(weather );

		return "welcome";
	}

	@GetMapping("/weathers")
	private List<Weather> printAllWeather( ) {

		return weatherRepository.findAll( );
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
