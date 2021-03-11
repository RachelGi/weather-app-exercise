package com.climacell.weather_app.service;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.climacell.weather_app.controller.StatisticField;
import com.climacell.weather_app.exception.NoDataFoundException;
import com.climacell.weather_app.model.Location;
import com.climacell.weather_app.model.Weather;
import com.climacell.weather_app.repository.WeatherRepository;

@Service
public class WeatherService {

	@Autowired //TODO check
	private WeatherRepository weatherRepository;



	public void importWeatherDataFromCSVFile(Path csvFile) {

	}

	public List<Weather> retrieveWeathersAtLocation(Location location)throws NoDataFoundException {
		return weatherRepository.findByLongitudeAndLatitude(location.getLongitude(), location.getLatitude());
	}

	public HashMap<StatisticField, Weather> retrieveSummarizeWeathersAtLocation(Location location) throws NoDataFoundException {
		HashMap<StatisticField, Weather> summarizeDataMap = new HashMap<>(); 
		
		summarizeDataMap.put(StatisticField.MIN, weatherRepository.getMinWeatherAtLocation(location.getLongitude(), location.getLatitude()));
		summarizeDataMap.put(StatisticField.MAX, weatherRepository.getMaxWeatherAtLocation(location.getLongitude(), location.getLatitude()));
		summarizeDataMap.put(StatisticField.AVG, weatherRepository.getAverageWeatherAtLocation(location.getLongitude(), location.getLatitude()));

		
		return summarizeDataMap;
	}

}
