package dk.lbloft.auth;

public interface UserProvider {
	User authenticate(String username, String password);
}
