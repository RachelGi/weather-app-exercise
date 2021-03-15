package com.climacell.weather_app.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.climacell.weather_app.exception.NoDataFoundException;
import com.climacell.weather_app.exception.ParseFileException;
import com.climacell.weather_app.model.Weather;
import com.climacell.weather_app.model.WeatherAtLocation;
import com.climacell.weather_app.model.WeatherSummarize;
import com.climacell.weather_app.service.WeatherService;

@RestController
public class WeatherForecastController {
	//TODO addon log, test

	@Autowired
	private WeatherService weatherService;
	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	Job job;


	@PostMapping("admin/load")
	public BatchStatus loadWeatherData(@RequestParam("fileName") String fileName) throws  ParseFileException {
		JobParameters params = new JobParametersBuilder()
				.addString("fileName", fileName).addDate("date", new Date())
				.toJobParameters();
		BatchStatus status; 
		try {
			
			status = jobLauncher.run(job, params).getStatus();
		}
		catch(Exception e) {
			status = BatchStatus.FAILED;
			throw new ParseFileException("Error to parse and save file " , e); 
		}
		return status;
	}

	@GetMapping("/weather/data") 
	private List<WeatherAtLocation> retrieveWeatherForecastByLocation(@RequestParam Double latitude, @RequestParam Double longitude, HttpServletResponse response ) throws IOException { 
		List<WeatherAtLocation> weatherList = null; 
		try {
			weatherList = weatherService.retrieveWeathersAtLocation(longitude, latitude);
		} catch (NoDataFoundException e) {
			response.sendError(HttpStatus.NOT_FOUND.value(), "No Weather forcast for location "+ + longitude + " " + latitude); //TODO check if NO_CONTENT
		}
		return weatherList;
	}

	@GetMapping("/weather/summarize") 
	private HashMap<SummarizeField,WeatherSummarize> retrieveWeatherForecastSummarizeByLocation(@RequestParam Double latitude, @RequestParam Double longitude,HttpServletResponse response) throws IOException{
		HashMap<SummarizeField,WeatherSummarize> summarizeWeather = null; 
		try {
			summarizeWeather = weatherService.retrieveSummarizeWeathersAtLocation(longitude, latitude);
		} catch (NoDataFoundException e) {
			response.sendError(HttpStatus.NOT_FOUND.value(), "No Weather forcast for location "+ longitude + " " +  latitude); 
		}
		return summarizeWeather;
	}

}
