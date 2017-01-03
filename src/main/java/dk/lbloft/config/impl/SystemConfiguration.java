package dk.lbloft.config.impl;

import java.util.Set;

import dk.lbloft.config.Configuration;

public class SystemConfiguration extends Configuration {
	@Override
	protected String internalGet(String key) {
		return System.getProperty(key);
	}

	@Override
	public Set<String> getKeys() {
		return System.getProperties().stringPropertyNames();
	}
}
