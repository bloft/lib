package dk.lbloft.auth;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Logger;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.google.common.base.Charsets;

import dk.lbloft.util.Tlv;
import dk.lbloft.util.Timer;

public class Passwords {
	private static final Logger logger = Logger.getLogger(Passwords.class.getName());
	private static final int saltLen = 32;
	private static final int desiredKeyLen = 256;
	private static final int defaultIterations = 50000;
	private static final Protocols defaultProtocol = Protocols.PBKDF2WithHmacSHA256;
	private static NewHashListener listener = null;

	private enum Keys {
		PROTOCOL, HASH, SALT, ITERATIONS
	}

	private enum Protocols {
		Plain,
		PBKDF2WithHmacSHA256
	}

	public static String newHash(String password) {
		try {
			Tlv klv = new Tlv();
			klv.put(Keys.PROTOCOL, defaultProtocol);
			klv.put(Keys.ITERATIONS, defaultIterations);
			klv.put(Keys.SALT, SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen));
			klv.put(Keys.HASH, hash(password, klv));
			return Base64.getEncoder().encodeToString(klv.getBytes());
		} catch (Exception e) {
			throw new RuntimeException("Unable to generate password hash", e);
		}
	}

	public static boolean check(String password, String passwordHash) {
		Tlv klv = Tlv.parse(Base64.getDecoder().decode(passwordHash));
		if(Arrays.equals(hash(password, klv), klv.get(Keys.HASH))) {
			if(klv.getInt(Keys.ITERATIONS) < defaultIterations && listener != null) {
				listener.newHash(passwordHash, Base64.getEncoder().encodeToString(klv.put(Keys.HASH, hash(password, klv)).getBytes()));
			}
			return true;
		}
		return false;
	}

	private static byte[] hash(String password, Tlv klv) {
		if (password == null || password.length() == 0) {
			throw new IllegalArgumentException("Empty passwords are not supported.");
		}
		byte[] hash = null;
		try {
			if(klv.getEnum(Keys.PROTOCOL, Protocols.class) == Protocols.Plain) {
				hash = password.getBytes(Charsets.UTF_8);
			} else {
				Timer timer = Timer.start();
				SecretKeyFactory f = SecretKeyFactory.getInstance(klv.getEnum(Keys.PROTOCOL, Protocols.class).name());
				SecretKey key = f.generateSecret(new PBEKeySpec(
						password.toCharArray(), klv.get(Keys.SALT), klv.getInt(Keys.ITERATIONS), desiredKeyLen)
				);
				hash = key.getEncoded();
				if(timer.getTime() < 100 && klv.getInt(Keys.ITERATIONS) == defaultIterations) {
					logger.warning("Hash function ended to fast... Adjustment to password.iterations needed");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Unable to generate password hash", e);
		}
		return hash;
	}

	public static void addListener(NewHashListener listener) {
		Passwords.listener = listener;
	}

	public interface NewHashListener {
		void newHash(String oldHash, String newHash);
	}

	public static void main(String[] args) {
		if(args.length == 1) {
			System.out.println(Passwords.newHash(args[0]));
		} else {
			System.err.println("Please supply a password");
		}
	}
}