package com.climacell.weather_app.model;

import java.sql.Date;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public  class Weather { //TODO @entity
	
	private double longitude; //TODO indx ? 
	private double latitude; //TODO idx?
	private Date forecastTime; //TODO check if import sql util
	private double temperature;
	private double precipitationRate;

}
