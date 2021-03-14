package com.climacell.weather_app.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void importWeatherDataFromCSVFile() throws FileNotFoundException, IOException, CsvException, URISyntaxException {
		System.err.println("=========================HERE=================");

		
		
		ColumnPositionMappingStrategy<Weather> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(Weather.class);
        String[] memberFieldsToBindTo = {"longitude", "latitude", "forecastTimeFromString", "temperature", "precipitation"};
        strategy.setColumnMapping(memberFieldsToBindTo);
		
        FileReader  fileReader = new FileReader(getFileFromRessourcesFolder("data/file1.csv")); 
        System.out.println("=====================FILE 1 ok ");
		 List<Weather> beans = new CsvToBeanBuilder<Weather>(fileReader)
	                .withMappingStrategy(strategy)
	                .withSkipLines(1)
	                .build()
	                .parse();
		 System.out.println("=====================PARRSE ! ok ");
//		 weatherRepository.saveAll(beans);
		 beans = new CsvToBeanBuilder<Weather>(new FileReader(getFileFromRessourcesFolder("data/file2.csv")))
	                .withMappingStrategy(strategy)
	                .withSkipLines(1)
	                .build()
	                .parse();
//		 weatherRepository.saveAll(beans);
		 beans = new CsvToBeanBuilder<Weather>(new FileReader(getFileFromRessourcesFolder("data/file3.csv")))
	                .withMappingStrategy(strategy)
	                .withSkipLines(1)
	                .build()
	                .parse();
//		 weatherRepository.saveAll(beans);
		
		
		
	}

	private File getFileFromRessourcesFolder(String dataFileRessourcePath) throws FileNotFoundException, URISyntaxException {
		URL ressourceUrl = WeatherService.class.getClassLoader().getResource(dataFileRessourcePath);
		if(ressourceUrl == null) {
			System.err.println("Cannot find file");
			throw new FileNotFoundException("cannot find file " + dataFileRessourcePath); 
		}
		return Paths.get(ressourceUrl.toURI()).toFile();
		
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
