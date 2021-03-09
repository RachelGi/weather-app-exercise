package com.climacell.weather_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherAppApplication {

	public static void main(String[] args) {
		System.out.println("=========Start=========");
		SpringApplication.run(WeatherAppApplication.class, args);
	}

}
