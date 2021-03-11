package com.climacell.weather_app.repository;

import com.climacell.weather_app.model.Weather;

public interface WeatherRepositoryCustom{
	public Weather getMinWeatherAtLocation(Double longitude, Double latitude);

	public Weather getMaxWeatherAtLocation(Double longitude, Double latitude);

	public Weather getAverageWeatherAtLocation(Double longitude, Double latitude);
}
