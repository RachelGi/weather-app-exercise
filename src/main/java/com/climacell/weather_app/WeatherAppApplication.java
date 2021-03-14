package com.climacell.weather_app;

import java.net.URISyntaxException;

import javax.annotation.PostConstruct;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
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
import com.climacell.weather_app.service.WeatherService;

//@SpringBootApplication
@EnableMongoAuditing
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class WeatherAppApplication {

	@Autowired 
	MongoDatabaseFactory mongoTemplateFactory;
	@Autowired
	private WeatherService weatherService;
	@Autowired
    JobLauncher jobLauncher;
	@Autowired
	Job job;

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
	public void initMockDatabase() throws MockParseFileException, URISyntaxException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException   {
//		try {
//			weatherService.importWeatherDataFromCSVFile();
			
			 JobParameters params = new JobParametersBuilder()
		                .addString("JobID", String.valueOf(System.currentTimeMillis()))
		                .toJobParameters();
		        jobLauncher.run(job, params);
		        
//		} catch (IOException | CsvException e) {
//			throw new MockParseFileException(null, e);//TODO
//		}
	}

}
