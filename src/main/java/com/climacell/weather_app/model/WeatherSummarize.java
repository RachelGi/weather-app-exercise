package com.climacell.weather_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherSummarize {
	private Double temperature;
	private Double precipitation;


}
