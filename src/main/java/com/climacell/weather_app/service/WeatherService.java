package com.climacell.weather_app.service;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.climacell.weather_app.controller.StatisticField;
import com.climacell.weather_app.exception.NoDataFoundException;
import com.climacell.weather_app.model.Weather;
import com.climacell.weather_app.repository.WeatherRepository;

@Service
public class WeatherService {

	@Autowired //TODO check
	private WeatherRepository weatherRepository;



	public void importWeatherDataFromCSVFile(Path csvFile) {

	}

	public List<Weather> retrieveWeathersAtLocation(double longitude, double latitude)throws NoDataFoundException {
		return weatherRepository.findByLongitudeAndLatitude(longitude, latitude);
	}

	public HashMap<StatisticField, Weather> retrieveSummarizeWeathersAtLocation(double longitude, double latitude) throws NoDataFoundException {
		HashMap<StatisticField, Weather> summarizeDataMap = new HashMap<>(); 
		
		summarizeDataMap.put(StatisticField.MIN, weatherRepository.getMinWeatherAtLocation(longitude, latitude));
		summarizeDataMap.put(StatisticField.MAX, weatherRepository.getMaxWeatherAtLocation(longitude, latitude));
		summarizeDataMap.put(StatisticField.AVG, weatherRepository.getAverageWeatherAtLocation(longitude, latitude));

		
		return summarizeDataMap;
	}

}
