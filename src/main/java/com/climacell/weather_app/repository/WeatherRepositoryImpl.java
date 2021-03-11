package com.climacell.weather_app.repository;

import org.springframework.stereotype.Repository;

import com.climacell.weather_app.model.Weather;

@Repository
public class WeatherRepositoryImpl implements WeatherRepositoryCustom{

	@Override
	public Weather getMinWeatherAtLocation(Double longitude, Double latitude) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Weather getMaxWeatherAtLocation(Double longitude, Double latitude) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Weather getAverageWeatherAtLocation(Double longitude, Double latitude) {
		// TODO Auto-generated method stub
		return null;
	}


}
