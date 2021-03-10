package com.climacell.weather_app.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public  class Weather { //TODO @entity
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //TODO
	private Double longitude; //TODO indx ? 
	private Double latitude; //TODO idx?
	private Date forecastTime; //TODO check if import sql util
	private Double temperature;
	private Double precipitationRate;

}
