package dk.lbloft.service;

import java.util.logging.Logger;

import dk.lbloft.option.Option;


public class TestOptions {
	private static final Logger logger = Logger.getLogger(TestOptions.class.getName());

	public static void main(String[] args) {
		Option o = new Option();
		o.addString("test", "This is a test");
		o.addString("test", "This is a test");
		o.parse(args);
	}
}