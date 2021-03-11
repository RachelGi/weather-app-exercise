package com.climacell.weather_app.model;

import java.sql.Date;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@CompoundIndex(def = "{'longitude':1, 'latitude':1}", name = "location_index")
public class Weather { 
	private Double longitude; 
	private Double latitude; 
	private Date forecastTime;
	private Double temperature;
	private Double precipitationRate;
}
