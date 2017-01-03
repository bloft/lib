package dk.lbloft.locate;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Locator {
	private static ConcurrentHashMap<String, Object> objects = new ConcurrentHashMap<String, Object>();
	
	public static <T> T locate(String name, Class<? extends T> serviceType) throws ClassNotFoundException {
		Object obj = objects.get(name);
		if(serviceType.isInstance(obj)) {
			return serviceType.cast(obj);
		}
		throw new ClassNotFoundException("Unable to find object of class " + serviceType.getName());
	}
	
	public static <T> List<T> locateAll(Class<? extends T> serviceType) {
		List<T> results = new ArrayList<T>();
		for (Object obj : objects.values()) {
			if(serviceType.isInstance(obj)) {
				results.add(serviceType.cast(obj));
			}
		}
		return results;
	}
	
	public static <T> T locate(Class<? extends T> serviceType) throws ClassNotFoundException {
		List<T> results = locateAll(serviceType);
		if(results.size() == 1) {
			return results.get(0);
		} else if (results.size() == 0) {
			throw new ClassNotFoundException("Unable to find object of class " + serviceType.getName());
		} else {
			throw new ClassNotFoundException("More then 1 element found of the specified class " + serviceType.getName());
		}
	}
	
	public static void register(Object object) {
		register(object.getClass().getCanonicalName(), object);
	}
	
	public static void register(String name, Object object) {
		objects.put(name, object);
	}
	
	public static void unregister(String name) {
		objects.remove(name);
	}
	
	public static void unregister(Object object) {
		objects.remove(object.getClass().getCanonicalName());
	}
}
