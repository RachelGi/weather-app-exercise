package com.climacell.weather_app.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public  class Weather { //TODO @entity
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //TODO
	@OneToOne()//fetch = FetchType.LAZY
	@JoinColumn(name="location_id",  referencedColumnName = "id")
	private Location location; //TODO indx ? 
	private Date forecastTime; //TODO check if import sql util
	private Double temperature;
	private Double precipitationRate;

}
