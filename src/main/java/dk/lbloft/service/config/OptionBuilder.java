package dk.lbloft.service.config;

import java.util.logging.Logger;

import dk.lbloft.option.Option;
import dk.lbloft.service.RuntimeBuilder;


public class OptionBuilder extends RuntimeBuilder<OptionCo> {
	private static final Logger logger = Logger.getLogger(OptionBuilder.class.getName());

	@Override
	public Object buildRuntime(OptionCo configObj) {
		Option option = new Option();
		// Add options from config file
		return option;
	}
}