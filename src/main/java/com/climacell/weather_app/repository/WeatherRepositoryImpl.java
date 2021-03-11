package com.climacell.weather_app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.climacell.weather_app.model.Weather;

@Repository
public class WeatherRepositoryImpl implements WeatherRepositoryCustom{

	@Autowired
	MongoTemplate mongoTemplate; 
	
	@Override
	public List<Weather> findByLongitudeAndLatitude(Double longitude, Double latitude) {
		Query q = new Query(); 
		q.withHint("location_index");
		q.addCriteria(new Criteria().andOperator(Criteria.where("longitude").is(longitude),Criteria.where("latitude").is(latitude)));
		return mongoTemplate.find(q, Weather.class);
	}

	@Override
	public Weather getMinWeatherAtLocation(Double longitude, Double latitude) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Weather getMaxWeatherAtLocation(Double longitude, Double latitude) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Weather getAverageWeatherAtLocation(Double longitude, Double latitude) {
		// TODO Auto-generated method stub
		return null;
	}



}
