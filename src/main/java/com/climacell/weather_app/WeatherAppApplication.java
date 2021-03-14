package com.climacell.weather_app;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.climacell.weather_app.exception.MockParseFileException;
import com.climacell.weather_app.service.WeatherService;
import com.opencsv.exceptions.CsvException;

@SpringBootApplication
public class WeatherAppApplication {

	@Autowired 
	MongoDatabaseFactory mongoTemplateFactory;
	@Autowired
	private WeatherService weatherService;

	public static void main(String[] args) {
		SpringApplication.run(WeatherAppApplication.class, args);
		System.out.println("=========Start=========");
	}

	@Bean 
	public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory,MongoMappingContext context) { 
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory); 
		return mongoTemplate; 
	}
	
	@PostConstruct
	public void initMockDatabase() throws MockParseFileException, URISyntaxException   {
		try {
			weatherService.importWeatherDataFromCSVFile();
		} catch (IOException | CsvException e) {
			throw new MockParseFileException(null, e);//TODO
		}
	}

}
