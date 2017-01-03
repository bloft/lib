package dk.lbloft.encryption;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.logging.Logger;

import javax.net.ServerSocketFactory;

import dk.lbloft.ssl.SSLContextFactory;


public class SSLSocketServerTest {
	private static final Logger logger = Logger.getLogger(SSLSocketServerTest.class.getName());


	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException, KeyManagementException {
		ServerSocketFactory ssf = SSLContextFactory.get("/home/bklo/qm.stibo_ssl.jks", "stibosw").getServerSocketFactory();
	    ServerSocket ss = ssf.createServerSocket(5432);
		System.out.println("Server started");
	    while (true) {
		    System.out.println("Waiting for client");
		    try (Socket s = ss.accept()){
			    System.out.println("New Client: " + s.getRemoteSocketAddress());
			    try (PrintStream out = new PrintStream(s.getOutputStream())) {
				    out.println("Hallo");
			    }
		    }
	    }
	}

}