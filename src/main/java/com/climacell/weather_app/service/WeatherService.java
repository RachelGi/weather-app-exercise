package com.climacell.weather_app.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.climacell.weather_app.controller.SummarizeField;
import com.climacell.weather_app.exception.MockParseFileException;
import com.climacell.weather_app.exception.NoDataFoundException;
import com.climacell.weather_app.model.WeatherAtLocation;
import com.climacell.weather_app.model.WeatherSummarize;
import com.climacell.weather_app.repository.WeatherRepository;


/**
 * Manage the weather data operations.
 * @author Rachel Guigui
 *
 */
@Service
public class WeatherService {

	@Autowired 
	private WeatherRepository weatherRepository;
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;
	public static final String JOB_RESOURCE_FILE_NAME_FIELD = "resourceFileName";


	/**
	 * Import CSV File into Database 
	 * @param resourceFileName - path of the CSV file to import, relative to the resource folder
	 * @return BatchStatus - returns the status of the job, {@code BatchStatus.FAILED}  if an exception occurred
	 * @throws MockParseFileException
	 */
	public BatchStatus importWeatherDataFromCSVFile(String resourceFileName) throws MockParseFileException{

		JobParameters params = new JobParametersBuilder()
				.addString(JOB_RESOURCE_FILE_NAME_FIELD, resourceFileName).addDate("date", new Date())
				.toJobParameters();
		BatchStatus status; 
		try {
			status = jobLauncher.run(job, params).getStatus();
		}
		catch(Exception e) {
			status = BatchStatus.FAILED;
			throw new MockParseFileException("Error to parse and save file " , e); 
		}
		return status;
	}


	/**
	 * Returns the weather forecast in a specific location 
	 * @param longitude (from -180 to  +180)
	 * @param latitude (from -90 to  +90)
	 * @return the weather forecast in a specific location 
	 * @throws NoDataFoundException if no data was found
	 */
	public List<WeatherAtLocation> retrieveWeathersAtLocation(double longitude, double latitude)throws NoDataFoundException {
		return weatherRepository.findByLongitudeAndLatitude(longitude, latitude);
	}


	/**
	 * Retrieve max,min,avg weather data for a specific location
	 * @param longitude (from -180 to  +180)
	 * @param latitude (from -90 to  +90)
	 * @throws NoDataFoundException
	 */
	public HashMap<SummarizeField, WeatherSummarize> retrieveSummarizeWeathersAtLocation(double longitude, double latitude) throws NoDataFoundException {
		HashMap<SummarizeField, WeatherSummarize> summarizeDataMap = new HashMap<>(); 

		summarizeDataMap.put(SummarizeField.MIN, weatherRepository.getMinWeatherAtLocation(longitude, latitude));
		summarizeDataMap.put(SummarizeField.MAX, weatherRepository.getMaxWeatherAtLocation(longitude, latitude));
		summarizeDataMap.put(SummarizeField.AVG, weatherRepository.getAverageWeatherAtLocation(longitude, latitude));


		return summarizeDataMap;
	}

}
