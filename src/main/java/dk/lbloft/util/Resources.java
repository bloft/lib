package dk.lbloft.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Resources {
	private static String servicesPrefix = "META-INF/services/";
	
	protected static List<String> readResource(URL url) {
		ArrayList<String> result = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			while((line = br.readLine()) != null) {
				if(!line.trim().startsWith("#")) {
					result.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public static List<String> getServices(String url) {
		ArrayList<String> result = new ArrayList<String>();
		Enumeration<URL> resources;
		try {
			resources = Resources.class.getClassLoader().getResources(servicesPrefix + url);
			while (resources.hasMoreElements()) {
				result.addAll(readResource(resources.nextElement()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static List<String> getServices(Class<?> clazz) {
		return getServices(clazz.getName());
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<Class<T>> getServiceClasses(Class<T> clazz) {
		ArrayList<Class<T>> result = new ArrayList<Class<T>>();
		for (String classString : getServices(clazz)) {
			try {
				Class<?> classInstance = Resources.class.getClassLoader().loadClass(classString);
				if(clazz.isAssignableFrom(classInstance)) {
					result.add((Class<T>) classInstance);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
