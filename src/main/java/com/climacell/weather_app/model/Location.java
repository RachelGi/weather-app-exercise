package com.climacell.weather_app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private Double longitude; //TODO indx ? 
	private Double latitude; //TODO idx?
}
