package com.climacell.weather_app.repository;

import java.util.List;

import com.climacell.weather_app.exception.NoDataFoundException;
import com.climacell.weather_app.model.Weather;
import com.climacell.weather_app.model.WeatherAtLocation;
import com.climacell.weather_app.model.WeatherSummarize;

public interface WeatherRepositoryCustom{
	public List<WeatherAtLocation> findByLongitudeAndLatitude(Double longitude, Double latitude) throws NoDataFoundException;



	
	public WeatherSummarize getMinWeatherAtLocation(Double longitude, Double latitude) throws NoDataFoundException;



	public WeatherSummarize getMaxWeatherAtLocation(Double longitude, Double latitude) throws NoDataFoundException;

	public WeatherSummarize getAverageWeatherAtLocation(Double longitude, Double latitude) throws NoDataFoundException;


	

}
