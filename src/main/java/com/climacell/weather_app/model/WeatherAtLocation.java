package com.climacell.weather_app.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherAtLocation extends WeatherSummarize{
	private Date forecastTime;
}
