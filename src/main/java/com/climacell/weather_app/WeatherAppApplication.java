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
		System.out.println("=========Start=========");
		SpringApplication.run(WeatherAppApplication.class, args);
	}

	@Bean 
	public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory,MongoMappingContext context) { 

//		MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context); 
//		converter.setTypeMapper(new DefaultMongoTypeMapper(null)); converter.setMapKeyDotReplacement("[@]"); 
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory); 
		return mongoTemplate; 
	}

}
