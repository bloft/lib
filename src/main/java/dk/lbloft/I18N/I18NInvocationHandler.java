package dk.lbloft.I18N;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.lbloft.config.CompositeConfiguration;
import dk.lbloft.config.InvalidConfigurationException;
import dk.lbloft.config.impl.INIConfiguration;
import dk.lbloft.util.Replacer;

public class I18NInvocationHandler implements InvocationHandler {
	private final static Logger logger = LoggerFactory.getLogger(I18NInvocationHandler.class);
	
	private String keyPrefix;
	private Locale locale;
	
	private static CompositeConfiguration localized = null;

	public I18NInvocationHandler(String keyPrefix) {
		this.keyPrefix = keyPrefix;
		locale = Locale.getDefault();
		
		if(localized == null) {
			load();
		}
	}
	
	private void load() {
		localized = new CompositeConfiguration();
		try {
			if(locale.getCountry() != null && locale.getCountry() != "") {
				if(locale.getVariant() != null && locale.getVariant() != "") {
					load(String.format("translation.%s_%s_%s.properties", locale.getLanguage(), locale.getCountry(), locale.getVariant()));
				}
				load(String.format("translation.%s_%s.properties", locale.getLanguage(), locale.getCountry()));
			}
			load(String.format("translation.%s.properties", locale.getLanguage()));
			load("translation.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void load(String filename) throws InvalidConfigurationException, IOException {
		File file = new File(filename);
		if(file.exists()) {
			logger.trace("Loading: " + file.getAbsolutePath());
			localized.append(new INIConfiguration(file));
		} else {
			logger.trace("Unable to locate translation: " + file.getAbsolutePath());
		}
	}
	
	private String getLocalized(String key, String defaultValue) {
		if(logger.isTraceEnabled()) {
			logger.trace("Key: " + key + " - default: " + defaultValue);
		}
		return localized.get(key, defaultValue);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(method.isAnnotationPresent(Message.class)) {
			if(!method.getReturnType().equals(String.class)) {
				throw new RuntimeException("Unknown return type, must be String for I18N translation to work");
			}
			
			HashMap<String, String> map = new HashMap<String, String>();
			Annotation[][] argAnnos = method.getParameterAnnotations();
			for (int i = 0; i < argAnnos.length; i++) {
				Annotation[] annotations = argAnnos[i];
				for (Annotation annotation : annotations) {
					if(annotation instanceof Arg) {
						map.put(((Arg)annotation).value(), args[i].toString());
					}
				}
			}
			
			Message m = method.getAnnotation(Message.class);
			
			String message = getLocalized(keyPrefix + "." + ("".equals(m.key()) ? method.getName() : m.key()), m.value());
			if(logger.isTraceEnabled()) {
				logger.trace("Message: " + message);
			}
			return Replacer.on().replace(map).build(message);
		} else {
			throw new RuntimeException("Message must be pressent to be able to use it for I18N translation");
		}
	}

}
