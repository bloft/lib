package dk.lbloft.config;

import java.util.HashSet;
import java.util.Set;

public class SubSectionConfiguration extends Configuration {
	
	private Configuration parent;
	private String keyPrefix;
	
	public SubSectionConfiguration(Configuration parent, String keyPrefix) {
		this.parent = parent;
		this.keyPrefix = keyPrefix;
	}

	@Override
	protected String internalGet(String key) {
		return parent.internalGet(keyPrefix + "." + key);
	}

	@Override
	public Set<String> getKeys() {
		HashSet<String> keys = new HashSet<String>();
		for (String key : parent.getKeys()) {
			if(key.startsWith(keyPrefix + ".")) {
				keys.add(key.substring(keyPrefix.length() + 1));
			}
		}
		return keys;
	}
}