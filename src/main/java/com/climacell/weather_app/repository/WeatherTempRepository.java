package com.climacell.weather_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.climacell.weather_app.model.WeatherTemp;

@Repository
public interface WeatherTempRepository extends JpaRepository<WeatherTemp, Integer>{

}
