package com.climacell.weather_app.model;

import java.sql.Date;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@CompoundIndexes({
    @CompoundIndex(name = "location_index", def = "{longitude':1, 'latitude':1}")
})//TODO not working! did manually
public class Weather { 
	private Double longitude; 
	private Double latitude; 
	private Date forecastTime;
	private Double temperature;
	private Double precipitation;
}
