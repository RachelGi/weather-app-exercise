package com.climacell.weather_app.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public  class WeatherTemp { //TODO @entity
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //TODO
	private Date forecastTime; //TODO check if import sql util
	private Double temperature;
	private Double precipitationRate;
	private Double longitude; //TODO indx ? 
	private Double latitude; //TODO idx?
}
