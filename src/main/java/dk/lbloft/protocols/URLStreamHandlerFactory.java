package dk.lbloft.protocols;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLStreamHandler;


public class URLStreamHandlerFactory implements java.net.URLStreamHandlerFactory {
	private java.net.URLStreamHandlerFactory factory;

	public URLStreamHandlerFactory(java.net.URLStreamHandlerFactory factory) {
		this.factory = factory;
	}

	private static java.net.URLStreamHandlerFactory getURLStreamHandlerFactory() {
		try {
			for (Field field : URL.class.getDeclaredFields()) {
				if (Modifier.isStatic(field.getModifiers()) && field.getType().equals(java.net.URLStreamHandlerFactory.class)) {
					field.setAccessible(true);
					return (java.net.URLStreamHandlerFactory) field.get(null);
				}
			}
		} catch (Exception e) {}
		return null;
	}

	/**
	 * Used to register the URLStreamHandlerFactory
	 * Alternative:
	 *  - System.setProperty("java.protocol.handler.pkgs", "dk.lbloft.protocols");
	 */
	public static void register() {
		java.net.URLStreamHandlerFactory oldFactory = getURLStreamHandlerFactory();
		if(!(oldFactory instanceof URLStreamHandlerFactory)) {
			URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory(oldFactory));
		}
	}

	@Override
	public URLStreamHandler createURLStreamHandler(String protocol) {
		try {
			Class<?> c = Class.forName(this.getClass().getPackage().getName() + "." + protocol + ".Handler");
			return (URLStreamHandler)c.getConstructor().newInstance();
		} catch (Exception e) {
			if(factory != null) {
				return factory.createURLStreamHandler(protocol);
			}
		}
		return null;
	}
}