package com.climacell.weather_app.model;

import java.sql.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
//@Table(name = "WEATHER",indexes = { @Index(name = "location_idx", columnList = "longitude,latitude")}) //TODO check
public  class Weather { 
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; 
	private Double longitude; 
	private Double latitude; 
	private Date forecastTime;
	private Double temperature;
	private Double precipitationRate;
}
