package com.climacell.weather_app.exception;

public class ParseFileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParseFileException(String message, Exception e) {
		super(message, e);
	}

}
