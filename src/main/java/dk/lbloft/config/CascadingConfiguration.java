package dk.lbloft.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class CascadingConfiguration extends Configuration {
	
	private Configuration source;
	private String keyPrefix;
	
	public CascadingConfiguration(Configuration source, String keyPrefix) {
		this.source = source;
		this.keyPrefix = keyPrefix;
	}

	@Override
	protected String internalGet(String key) {
		for (String prefix : getPaths(keyPrefix)) {
			String cKey = Joiner.on('.').skipNulls().join(prefix, key);
			if(source.contains(cKey)) {
				return source.internalGet(cKey);
			}
		}
		return null;
	}

	@Override
	public Set<String> getKeys() {
		HashSet<String> keys = new HashSet<String>();
		for (String prefix : getPaths(keyPrefix)) {
			if(prefix != null) {
				keys.addAll(source.getSubSection(prefix).getKeys());
			} else {
				keys.addAll(source.getKeys());
			}
		}
		return keys;
	}
	
	private Set<String> getPaths(String config) {
		LinkedHashSet<String> paths = new LinkedHashSet<String>();
		ArrayList<String> tmp = Lists.newArrayList(Splitter.on('.').split(config));
		for (int i = 0; i < tmp.size(); i++) {
			paths.add(Joiner.on('.').join(tmp.subList(i, tmp.size())));
		}
		for (int i = tmp.size(); i > 0; i--) {
			paths.add(Joiner.on('.').join(tmp.subList(0, i)));
		}
		paths.add(null);
		return paths;
	}
}
