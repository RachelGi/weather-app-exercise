package com.climacell.weather_app.repository;

import java.util.List;

import com.climacell.weather_app.model.Weather;
import com.climacell.weather_app.model.WeatherSummarize;

public interface WeatherRepositoryCustom{
	public List<Weather> findByLongitudeAndLatitude(Double longitude, Double latitude);

	
	public WeatherSummarize getMinWeatherAtLocation(Double longitude, Double latitude);

	public WeatherSummarize getMaxWeatherAtLocation(Double longitude, Double latitude);

	public WeatherSummarize getAverageWeatherAtLocation(Double longitude, Double latitude);
	

}
