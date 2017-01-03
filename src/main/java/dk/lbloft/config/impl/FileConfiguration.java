package dk.lbloft.config.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.Set;

import dk.lbloft.config.InvalidConfigurationException;

public abstract class FileConfiguration extends PropertiesConfiguration {
	private static String COMMENT_CHARS = "#;/!";

	private URL url;
	
	public FileConfiguration(File file) throws InvalidConfigurationException, IOException {
		this(file.toURI().toURL());
	}
	
	public FileConfiguration(URL url) throws InvalidConfigurationException, IOException {
		this.url = url;
		reload();
	}
	
	public void reload() throws InvalidConfigurationException, IOException {
		try(InputStream is = url.openStream()) {
			properties = readFile(is);
		}
	}
	
	protected boolean isCommentOrBlank(String line) {
		return line.trim().length() == 0 || COMMENT_CHARS.indexOf(line.trim().charAt(0)) >= 0;
	}
	
	protected String stripComment(String line) {
		return line;
	}
	
	protected abstract Properties readFile(InputStream is) throws InvalidConfigurationException;

	@Override
	protected String internalGet(String key) {
		// Check if need to reload
		return super.internalGet(key);
	}

	@Override
	public Set<String> getKeys() {
		// Check if need to reload
		return super.getKeys();
	}
}
