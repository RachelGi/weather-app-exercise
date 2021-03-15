package com.climacell.weather_app.parserUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.batch.item.ItemProcessor;

import com.climacell.weather_app.model.Weather;

/**
 * Class for transformation of the WeatherFromCsv object as written in the mock CSV file 
 * to Weather object as saved in the database
 * @author Rachel Guigui
 *
 */
public class WeatherItemProcessor implements ItemProcessor<WeatherFromCsv, Weather>{

	@Override
	public Weather process(WeatherFromCsv item) throws Exception {
		Weather weather = new Weather(); 
		weather.setLatitude(item.getLatitude());
		weather.setLongitude(item.getLongitude());
		weather.setPrecipitation(item.getPrecipitation());
		weather.setTemperature(item.getTemperature());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		weather.setForecastTime(df.parse(item.getForecastTimeFromString()));
		return weather;
	}

}
