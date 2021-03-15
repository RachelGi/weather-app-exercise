package com.climacell.weather_app.csvParse;

import lombok.Data;

@Data
public class WeatherFromCsv {

	private Double longitude; 
	private Double latitude; 
	String forecastTimeFromString;
	private Double temperature;
	private Double precipitation;
	
	
	
}
