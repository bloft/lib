package dk.lbloft.ssl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Logger;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;


public class SSLContextFactory {
	private static final Logger logger = Logger.getLogger(SSLContextFactory.class.getName());

	public static SSLContext get() throws NoSuchAlgorithmException {
		return SSLContext.getDefault();
	}

	public static SSLContext get(String keyStoreLocation, String password) throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
		return get(keyStoreLocation, keyStoreLocation, password);
	}

	public static SSLContext get(String keyStoreLocation, String trustStoreLocation, String password) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
		SSLContext sc = SSLContext.getInstance("TLSv1.2");

		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		keyManagerFactory.init(createKeyStore(keyStoreLocation, password), password.toCharArray());

		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustManagerFactory.init(createKeyStore(trustStoreLocation, password));

		sc.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
		return sc;
	}

	private static KeyStore createKeyStore(String location, String password) throws KeyStoreException {
		KeyStore keyStore = KeyStore.getInstance("jks");
		try (InputStream is = new FileInputStream(location)) {
			keyStore.load(is, password.toCharArray());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return keyStore;
	}
}