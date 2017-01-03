package dk.lbloft.config.impl;

import java.util.Set;
import java.util.logging.Logger;

import dk.lbloft.config.Configuration;


public class EnvConfiguration extends Configuration {
	private static final Logger logger = Logger.getLogger(EnvConfiguration.class.getName());

	@Override
	protected String internalGet(String key) {
		return System.getenv(key);
	}

	@Override
	public Set<String> getKeys() {
		return System.getenv().keySet();
	}
}