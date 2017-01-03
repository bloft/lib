package dk.lbloft.service;

import java.util.logging.Logger;

import dk.lbloft.config.impl.PropertiesConfiguration;
import dk.lbloft.auth.Passwords;
import dk.lbloft.auth.User;
import dk.lbloft.auth.impl.FileUserProvider;

public class Test {
	private static final Logger logger = Logger.getLogger(Test.class.getName());


	public static void main(String[] args) throws Exception {
		PropertiesConfiguration cfg = new PropertiesConfiguration();
		cfg.put("bjarne", Passwords.newHash("123"));
		cfg.put("bjarne.fullname", "Bjarne Loft");
		FileUserProvider userProvider = new FileUserProvider(cfg);
		User user = userProvider.authenticate("bjarne", "123");
		if(user == null) {
			System.out.println("Invalid username or password");
		} else {
			System.out.println("User: " + user);
		}
	}
}