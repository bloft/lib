package dk.lbloft.auth.impl;

import java.util.Properties;
import java.util.logging.Logger;

import dk.lbloft.config.Configuration;
import dk.lbloft.auth.Passwords;
import dk.lbloft.auth.User;
import dk.lbloft.auth.UserProvider;


public class FileUserProvider implements UserProvider {
	private static final Logger logger = Logger.getLogger(FileUserProvider.class.getName());

	private Configuration config;

	public FileUserProvider(Configuration config) {
		this.config = config;
	}

	@Override
	public User authenticate(String username, String password) {
		String hash = config.get(username, "");
		if(!"".equals(hash) && Passwords.check(password, hash)) {
			Configuration userConfig = config.getSubSection(username);
			Properties prop = new Properties();
			for (String key : userConfig.getKeys()) {
				prop.put(key, userConfig.get(key));
			}
			return new User(username, prop);
		}
		return null;
	}
}