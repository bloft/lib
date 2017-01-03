package dk.lbloft.protocols.aes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import dk.lbloft.encryption.Encrypter;

public class Handler extends URLStreamHandler {
	@Override
	protected URLConnection openConnection(URL u) throws IOException {
		return new URLConnection(u) {
			@Override
			public void connect() throws IOException {}
			
			@Override
			public InputStream getInputStream() throws IOException {
				try {
					return Encrypter.getEncryptedInputStream(new FileInputStream(getURL().getFile()));
				} catch (Exception e) {
					throw new IOException("Unable to create decrypted input stream", e);
				}
			}
			
			@Override
			public OutputStream getOutputStream() throws IOException {
				try {
					return Encrypter.getEncryptedOutputStream(new FileOutputStream(getURL().getFile()));
				} catch (Exception e) {
					throw new IOException("Unable to create encrypted output stream", e);
				}
			}
		};
	}
}
