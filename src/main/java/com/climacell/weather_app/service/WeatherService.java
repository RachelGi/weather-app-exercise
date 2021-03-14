package com.climacell.weather_app.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.climacell.weather_app.controller.StatisticField;
import com.climacell.weather_app.exception.NoDataFoundException;
import com.climacell.weather_app.model.Weather;
import com.climacell.weather_app.model.WeatherSummarize;
import com.climacell.weather_app.repository.WeatherRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

@Service
public class WeatherService {

	@Autowired //TODO check
	private WeatherRepository weatherRepository;


//Path csvFile
	public void importWeatherDataFromCSVFile() throws FileNotFoundException, IOException, CsvException {
//		  try (CSVReader reader = new CSVReader(new FileReader("C:\\Users\\rache\\Downloads\\file2.csv"))) {
//		      List<String[]> r = reader.readAll();
//		      System.out.println(r.size());
//		  }
//		long start = System.currentTimeMillis();
//		 List<Weather> beans = new CsvToBeanBuilder(new FileReader("C:\\Users\\rache\\Downloads\\file1.csv"))
//	                .withType(Weather.class)
//	                .build()
//	                .parse();
//		 weatherRepository.saveAll(beans);
//		 System.err.println("end " + (System.currentTimeMillis() - start));
//		 System.out.println(beans.size());
		
		
		
		
		
	}

	public List<Weather> retrieveWeathersAtLocation(double longitude, double latitude)throws NoDataFoundException {
		return weatherRepository.findByLongitudeAndLatitude(longitude, latitude);
	}

	public HashMap<StatisticField, WeatherSummarize> retrieveSummarizeWeathersAtLocation(double longitude, double latitude) throws NoDataFoundException {
		HashMap<StatisticField, WeatherSummarize> summarizeDataMap = new HashMap<>(); 
		
		summarizeDataMap.put(StatisticField.MIN, weatherRepository.getMinWeatherAtLocation(longitude, latitude));
		summarizeDataMap.put(StatisticField.MAX, weatherRepository.getMaxWeatherAtLocation(longitude, latitude));
		summarizeDataMap.put(StatisticField.AVG, weatherRepository.getAverageWeatherAtLocation(longitude, latitude));

		
		return summarizeDataMap;
	}

}
