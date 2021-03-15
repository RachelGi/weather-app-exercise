package com.climacell.weather_app.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Transient;

//import java.sql.Date;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@CompoundIndexes({
    @CompoundIndex(name = "location_index", def = "{longitude':1, 'latitude':1}")
})//TODO not working! did manually
//TODO add indx uniq : location & date
public class Weather { 
	private Double longitude; 
	private Double latitude; 
	private Date forecastTime;
	private Double temperature;
	private Double precipitation;
	
	
	
	public void setForecastTimeFromString(String date){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			this.forecastTime = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
