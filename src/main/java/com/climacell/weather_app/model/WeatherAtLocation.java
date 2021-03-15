package com.climacell.weather_app.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherAtLocation extends WeatherSummarize{
	private Date forecastTime;

	public WeatherAtLocation(Double temperature, Double precipitation, Date forecastTime) {
		super(temperature, precipitation);
		this.forecastTime = forecastTime;
	}
	
	
}
