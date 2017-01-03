package dk.lbloft.auth;

import java.io.Serializable;
import java.util.Properties;

import com.google.common.base.Objects;

public class User implements Serializable, Comparable<User> {
	private static final long serialVersionUID = 1L;
	private final String username;
	private Properties properties = new Properties();
	
	public User(String username, Properties properties) {
		this.username = username;
		this.properties = properties;
	}

	public String getUsername() {
		return username;
	}

	public Properties getProperties() {
		return properties;
	}
	
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	@Override
	public int compareTo(User o) {
		return getUsername().compareTo(o.getUsername());
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("Username", getUsername())
				.add("Properties", properties)
				.toString();
	}
}
