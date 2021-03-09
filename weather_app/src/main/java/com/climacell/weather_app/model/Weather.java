package com.climacell.weather_app.model;

import java.sql.Date;

import lombok.Data;

public @Data class Weather { //TODO @entity
	
	private double longitude;
	private double latitude;
	private Date forecastTime; //TODO check if import sql util
	private double temperature;
	private double precipitationRate;

}
