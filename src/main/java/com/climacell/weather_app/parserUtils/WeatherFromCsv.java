package com.climacell.weather_app.parserUtils;

import lombok.Data;

@Data
public class WeatherFromCsv {

	public Double longitude; 
	public Double latitude; 
	public String forecastTimeFromString;
	public Double temperature;
	public Double precipitation;
	
	
	
}
