package com.climacell.weather_app.controller;

public enum SummarizeField { 

	MIN,MAX,AVG;
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
