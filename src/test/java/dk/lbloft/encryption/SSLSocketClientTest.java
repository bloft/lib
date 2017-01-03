package dk.lbloft.encryption;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.logging.Logger;

import javax.net.SocketFactory;

import dk.lbloft.ssl.SSLContextFactory;


public class SSLSocketClientTest {
	private static final Logger logger = Logger.getLogger(SSLSocketClientTest.class.getName());

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException, KeyManagementException {
		SocketFactory sf = SSLContextFactory.get("/home/bklo/client.jks", "stibosw").getSocketFactory();
		Socket socket = sf.createSocket("localhost", 5432);
		try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			System.out.println(in.readLine());
		}
	}
}