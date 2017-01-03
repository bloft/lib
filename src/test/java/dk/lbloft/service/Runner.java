package dk.lbloft.service;

import java.net.MalformedURLException;
import java.util.logging.Logger;


public class Runner {
	private static final Logger logger = Logger.getLogger(Runner.class.getName());

	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
		Application app = new Application();
		app.start();
	}
}