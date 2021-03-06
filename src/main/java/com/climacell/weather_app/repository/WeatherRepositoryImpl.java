package com.climacell.weather_app.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.climacell.weather_app.model.WeatherSummarize;
import com.climacell.weather_app.exception.NoDataFoundException;
import com.climacell.weather_app.model.Weather;
import com.climacell.weather_app.model.WeatherAtLocation;

@Repository
public class WeatherRepositoryImpl implements WeatherRepositoryCustom{

	private static final String LATITUDE_DB_FIELD = "latitude";
	private static final String LONGITUDE_DB_FIELD = "longitude";
	@Autowired
	MongoTemplate mongoTemplate; 

	@Override
	public List<WeatherAtLocation> findByLongitudeAndLatitude(Double longitude, Double latitude) throws NoDataFoundException {
		Query q = new Query(); 
		q.withHint("location_index");
		q.addCriteria(new Criteria().andOperator(Criteria.where(LONGITUDE_DB_FIELD).is(longitude),Criteria.where(LATITUDE_DB_FIELD).is(latitude)));
		q.fields().exclude(LONGITUDE_DB_FIELD, LATITUDE_DB_FIELD);
		List<Weather> res = mongoTemplate.find(q, Weather.class);

		if(res.isEmpty()) {
			throw new NoDataFoundException("No Data was found for Longitude " + longitude + " and latitude " + latitude );
		}
		List<WeatherAtLocation> weatherAtLocationList  = res.stream().map(w -> new WeatherAtLocation(w.getTemperature(), w.getPrecipitation() , w.getForecastTime())).collect(Collectors.toList());
		
		 return weatherAtLocationList;
	}

	@Override
	public WeatherSummarize getMinWeatherAtLocation(Double longitude, Double latitude) throws NoDataFoundException {
		GroupOperation grpOpe = Aggregation.group(LONGITUDE_DB_FIELD).min("temperature").as("temperature").min("precipitation").as("precipitation");
		return getAggregationForSummarizeWeather(longitude, latitude, grpOpe);
	}

	@Override
	public WeatherSummarize getMaxWeatherAtLocation(Double longitude, Double latitude) throws NoDataFoundException {
		GroupOperation grpOpe = Aggregation.group(LONGITUDE_DB_FIELD).max("temperature").as("temperature").max("precipitation").as("precipitation");
		return getAggregationForSummarizeWeather(longitude, latitude, grpOpe);

	}

	@Override
	public WeatherSummarize getAverageWeatherAtLocation(Double longitude, Double latitude) throws NoDataFoundException {
		GroupOperation grpOpe = Aggregation.group(LONGITUDE_DB_FIELD).avg("temperature").as("temperature").avg("precipitation").as("precipitation");
		WeatherSummarize weatherSummarize =  getAggregationForSummarizeWeather(longitude, latitude, grpOpe);
		weatherSummarize.setPrecipitation(roundToOnDigit(weatherSummarize.getPrecipitation()));
		weatherSummarize.setTemperature(roundToOnDigit(weatherSummarize.getTemperature()));
		return weatherSummarize;
	}

	private WeatherSummarize getAggregationForSummarizeWeather(Double longitude, Double latitude,
			GroupOperation grpOpe) throws NoDataFoundException {
		MatchOperation matchAgg = Aggregation.match(new Criteria().
				andOperator(Criteria.where(LONGITUDE_DB_FIELD).is(longitude),Criteria.where(LATITUDE_DB_FIELD).is(latitude)));
		ProjectionOperation projectionOpe = Aggregation.project().andExclude("_id");
		Aggregation aggregation = Aggregation.newAggregation(matchAgg,
				grpOpe, projectionOpe);
		AggregationResults<WeatherSummarize> t = mongoTemplate.aggregate(aggregation, Weather.class, WeatherSummarize.class);
		List<WeatherSummarize> result = t.getMappedResults();
		if(result.isEmpty()) {
			throw new NoDataFoundException("No Data was found for Longitude " + longitude + " and latitude " + latitude );
		}
		 return result.get(0);
	}


	private double roundToOnDigit(double val) {
		return Math.round(val * 10) / 10.0; 
	}



}