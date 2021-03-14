package com.climacell.weather_app.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.climacell.weather_app.controller.StatisticField;
import com.climacell.weather_app.exception.NoDataFoundException;
import com.climacell.weather_app.model.Weather;
import com.climacell.weather_app.model.WeatherSummarize;
import com.climacell.weather_app.repository.WeatherRepository;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

@Service
public class WeatherService {

	@Autowired //TODO check
	private WeatherRepository weatherRepository;


//Path csvFile
	@SuppressWarnings("unchecked")
	public void importWeatherDataFromCSVFile() throws FileNotFoundException, IOException, CsvException {
		ColumnPositionMappingStrategy<Weather> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(Weather.class);
        String[] memberFieldsToBindTo = {"longitude", "latitude", "forecastTimeFromString", "temperature", "precipitation"};
        strategy.setColumnMapping(memberFieldsToBindTo);
		
		 List<Weather> beans = new CsvToBeanBuilder<Weather>(new FileReader("data/file1.csv"))
	                .withMappingStrategy(strategy)
	                .withSkipLines(1)
	                .build()
	                .parse();
		 weatherRepository.saveAll(beans);
		 beans = new CsvToBeanBuilder<Weather>(new FileReader("data/file2.csv"))
	                .withMappingStrategy(strategy)
	                .withSkipLines(1)
	                .build()
	                .parse();
		 weatherRepository.saveAll(beans);
		 beans = new CsvToBeanBuilder<Weather>(new FileReader("data/file3.csv"))
	                .withMappingStrategy(strategy)
	                .withSkipLines(1)
	                .build()
	                .parse();
		 weatherRepository.saveAll(beans);
		
		
		
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
