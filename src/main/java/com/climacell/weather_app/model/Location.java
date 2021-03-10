package com.climacell.weather_app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "LOCATION",indexes = { @Index(name = "location_idx", columnList = "longitude,latitude")}) //TODO check
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private Double longitude; //TODO indx ? 
	private Double latitude; //TODO idx?
}
