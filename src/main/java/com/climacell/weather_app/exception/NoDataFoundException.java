package com.climacell.weather_app.exception;

/**
 * Thrown when the requested data doesn't existed
 * @author Rachel Guigui
 *
 */
public class NoDataFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public NoDataFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoDataFoundException(String message) {
		super(message);
	}

}
