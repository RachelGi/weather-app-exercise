package com.climacell.weather_app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.climacell.weather_app.model.Weather;

@Repository
public interface WeatherRepository extends MongoRepository<Weather, Integer> , WeatherRepositoryCustom{


}
