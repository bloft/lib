package dk.lbloft.service.config;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.lbloft.config.CompositeConfiguration;
import dk.lbloft.config.impl.INIConfiguration;
import dk.lbloft.config.impl.SystemConfiguration;
import dk.lbloft.service.RuntimeBuilder;

public class ConfigurationBuilder extends RuntimeBuilder<ConfigurationCo> {
	private static Logger logger = LoggerFactory.getLogger(ConfigurationBuilder.class);

	@Override
	public Object buildRuntime(ConfigurationCo configObj) {
		CompositeConfiguration config = new CompositeConfiguration();
		config.append(new SystemConfiguration());
		for (URL url : configObj.getUrls()) {
			try {
				logger.trace("Loading configuration from: " + url);
				config.append(new INIConfiguration(url));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return config;
	}

}
