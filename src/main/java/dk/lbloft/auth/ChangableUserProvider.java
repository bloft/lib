package dk.lbloft.auth;

import java.util.List;
import java.util.Properties;

public interface ChangableUserProvider extends UserProvider {
	List<User> getAvailable();
	void create(User user, String password);
	void update(User user, Properties properties);
	void delete(User user);
}