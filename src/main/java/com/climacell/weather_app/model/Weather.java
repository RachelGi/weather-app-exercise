package com.climacell.weather_app.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Weather collection mapping
 * Assumption :  For a given time and location (longitude and latitude), 
 * there are no more than one document (the "data_unique_index" index is unique)
 * @author Rachel Guigui
 *
 */
@Document
@Data
@NoArgsConstructor
@CompoundIndexes({
	@CompoundIndex(name = "location_index", def = "{'longitude':1, 'latitude':1}"),
	@CompoundIndex(name = "data_unique_index", def = "{'longitude':1, 'latitude':1, 'forecastTime':1}", unique = true),

})

public class Weather { 
	private Double longitude; 
	private Double latitude; 
	private Date forecastTime;
	private Double temperature;
	private Double precipitation;

	public static final double MAX_LONGITUDE = 180.0;
	public static final double MIN_LONGITUDE = -180.0;
	public static final double MIN_LATITUDE = -90.0;
	public static final double MAX_LATITUDE = 90.0;
	


	public void setForecastTimeFromString(String date){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			this.forecastTime = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static boolean isValideLocation(Double latitude, Double longitude) {

		return latitude <= MAX_LATITUDE && latitude >= MIN_LATITUDE && longitude >= MIN_LONGITUDE &&  longitude <= MAX_LONGITUDE;
	}

}
