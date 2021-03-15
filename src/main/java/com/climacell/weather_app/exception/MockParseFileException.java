package com.climacell.weather_app.exception;

/**
 * Thrown when an issue occurs during the parsing of the mock file data
 * @author Rachel Guigui
 *
 */
public class MockParseFileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public MockParseFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public MockParseFileException(String message) {
		super(message);
	}

}
