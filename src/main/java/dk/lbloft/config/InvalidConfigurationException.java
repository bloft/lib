package dk.lbloft.config;

import java.io.IOException;

public class InvalidConfigurationException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public InvalidConfigurationException(String message) {
		super(message);
	}
	
	public InvalidConfigurationException(int lineNumber, String line) {
		super("Invalid configuration line[" + lineNumber + "]: " + line);
	}

	public InvalidConfigurationException(IOException e) {
		super(e);
	}

}
