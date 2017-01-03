package dk.lbloft.config.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import dk.lbloft.config.InvalidConfigurationException;

public class INIConfiguration extends FileConfiguration {

	public INIConfiguration(File file) throws InvalidConfigurationException, IOException {
		super(file);
	}
	
	public INIConfiguration(URL url) throws InvalidConfigurationException, IOException {
		super(url);
	}

	@Override
	protected Properties readFile(InputStream is) throws InvalidConfigurationException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		Properties data = new Properties();
		
		String section = "";
		String line;
		int lineNumber = 0;
		try {
			while((line = reader.readLine()) != null) {
				lineNumber++;
				if(!isCommentOrBlank(line)) {
					line = stripComment(line).trim();
					if(line.startsWith("[")) {
						if(line.endsWith("]")) {
							String newSection = line.substring(1, line.length() - 1);
							if(newSection.startsWith(".")) {
								section += newSection.substring(1) + ".";
							} else {
								section = newSection + ".";
							}
						} else {
							System.out.println("Invalid data: " + line);
							throw new InvalidConfigurationException(lineNumber, line);
						}
					} else if(line.indexOf("=") > 0) {
						String key = section + line.substring(0, line.indexOf("="));
						String value = line.substring(line.indexOf("=") + 1);
						if(key.endsWith("[]")) {
							key = key.substring(0, key.length() - 2);
							if(data.containsKey(key)) {
								value = data.get(key) + "," + value;
							}
						}
						data.put(key, value);
					} else {
						throw new InvalidConfigurationException(lineNumber, line);
					}
				} else {
//					System.out.println("Comment: " + line);
				}
			}
		} catch (IOException e) {
			throw new InvalidConfigurationException(e);
		}
		
		return data;
	}

}
