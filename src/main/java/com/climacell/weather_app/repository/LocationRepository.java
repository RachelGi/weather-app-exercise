package com.climacell.weather_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.climacell.weather_app.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer>{

}
