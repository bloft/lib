package dk.lbloft.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Lists;

public class CompositeConfiguration extends Configuration {
	
	private ArrayList<Configuration> configs;
	
	public CompositeConfiguration(Configuration... configs) {
		this.configs = Lists.newArrayList(configs);
	}
	
	public void append(Configuration config) {
		configs.add(config);
	}

	@Override
	protected String internalGet(String key) {
		for (Configuration config : configs) {
			if(config.contains(key)) {
				return config.internalGet(key);
			}
		}
		return null;
	}

	@Override
	public Set<String> getKeys() {
		HashSet<String> keys = new HashSet<String>();
		for (Configuration config : configs) {
			keys.addAll(config.getKeys());
		}
		return keys;
	}

}
