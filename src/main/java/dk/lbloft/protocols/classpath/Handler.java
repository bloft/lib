package dk.lbloft.protocols.classpath;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class Handler extends URLStreamHandler {
	private final ClassLoader classLoader;
    
    public Handler() {
    	this(null);
    }
    
    public Handler(ClassLoader classLoader) {
        this.classLoader = classLoader == null ? getClass().getClassLoader() : classLoader;
    }

	@Override
	protected URLConnection openConnection(URL u) throws IOException {
		final String path = u.getPath();
		final URL resourceUrl = classLoader.getResource(path.startsWith("/") ? path.substring(1) : path);
        if (resourceUrl == null) {
        	throw new FileNotFoundException("Unable to find classpath resource: " + path);
        }
        return resourceUrl.openConnection();
	}

}
