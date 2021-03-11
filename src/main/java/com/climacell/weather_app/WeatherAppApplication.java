package com.climacell.weather_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@SpringBootApplication
public class WeatherAppApplication {

	@Autowired 
	MongoDatabaseFactory mongoTemplateFactory;

	public static void main(String[] args) {
		SpringApplication.run(WeatherAppApplication.class, args);
		System.out.println("=========Start=========");
	}

	@Bean 
	public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory,MongoMappingContext context) { 
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory); 
		return mongoTemplate; 
	}

}
