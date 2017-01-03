package dk.lbloft.auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class CompositeUserProvider implements UserProvider {
	private static final Logger logger = Logger.getLogger(CompositeUserProvider.class.getName());

	private ArrayList<UserProvider> providers;

	public CompositeUserProvider(UserProvider ... providers) {
		this.providers = new ArrayList<UserProvider>(Arrays.asList(providers));
	}

	public void add(UserProvider provider) {
		providers.add(provider);
	}

	@Override
	public User authenticate(String username, String password) {
		for (UserProvider provider : providers) {
			User user = provider.authenticate(username, password);
			if(user != null) {
				return user;
			}
		}
		return null;
	}
}