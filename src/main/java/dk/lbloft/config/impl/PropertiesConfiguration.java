package dk.lbloft.config.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import dk.lbloft.config.Configuration;

public class PropertiesConfiguration extends Configuration {
	
	protected Properties properties = new Properties();
	
	public PropertiesConfiguration() {
		this(new Properties());
	}

	public PropertiesConfiguration(Properties properties) {
		this.properties = properties;
	}
	
	public void put(String key, String value) {
		properties.put(key, value);
	}
	
	public PropertiesConfiguration(String file) throws IOException {
		properties.load(new FileInputStream(new File(file)));
	}

	@Override
	protected String internalGet(String key) {
		return properties.getProperty(key);
	}

	@Override
	public Set<String> getKeys() {
		HashSet<String> keys = new HashSet<String>();
		for (Object string : properties.keySet()) {
			keys.add(string.toString());
		}
		return keys;
	}

}
