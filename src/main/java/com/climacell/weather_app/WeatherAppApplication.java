package com.climacell.weather_app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.climacell.weather_app.exception.MockParseFileException;
import com.climacell.weather_app.parserUtils.JobCompletionNotificationListener;
import com.climacell.weather_app.service.WeatherService;


/**
 *  Web service used to get the weather forecast and statistics for a
 * 	specific location
 *  The weather data is get from CSV file and save into DataBase
 * 	@author Rachel Guigui
 */
@EnableMongoAuditing
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class WeatherAppApplication {

	@Autowired 
	MongoDatabaseFactory mongoTemplateFactory;
	@Autowired
	private WeatherService weatherService;
	private static final Logger log =
			LoggerFactory.getLogger(WeatherAppApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(WeatherAppApplication.class, args);
	}

	@Bean 
	public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory,MongoMappingContext context) { 
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory); 
		log.info("init MongoTemplate");
		return mongoTemplate; 
	}

	
	
	/**
	 * Init the database with mock data csv file from the resource/data folder
	 * Parse and save the weather data into weather collection
	 * @throws MockParseFileException if an error occurred during the parsing of the data file
	 */
	@PostConstruct
	public void initMockDatabase() throws MockParseFileException   {
	
		log.info("init Mock Data");

		ExecutorService exec = Executors.newFixedThreadPool(3);
		exec.submit( () -> {
			return weatherService.importWeatherDataFromCSVFile("data/file1.csv"); 
		});
		exec.submit( () -> {
			return weatherService.importWeatherDataFromCSVFile("data/file2.csv"); 
		});
		exec.submit( () -> {
			return weatherService.importWeatherDataFromCSVFile("data/file3.csv"); 
		});

		exec.shutdown(); 
		
	}


}
