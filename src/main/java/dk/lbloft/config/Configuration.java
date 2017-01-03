package dk.lbloft.config;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dk.lbloft.config.converter.Converter;
import dk.lbloft.config.converter.EnumConverter;
import dk.lbloft.config.converter.ListConverter;
import dk.lbloft.config.converter.TimeAndUnitConverter;
import dk.lbloft.config.converter.URLConverter;

public abstract class Configuration {
	
	protected abstract String internalGet(String key);
	public abstract Set<String> getKeys();
	
	public boolean contains(String key) {
		return getKeys().contains(key);
	}
	
	public SubSectionConfiguration getSubSection(String key) {
		return new SubSectionConfiguration(this, key);
	}
	
	
	// Getter 

	public <T> T get(String key, Converter<T> converter) {
		String value = internalGet(key);
		try {
			return converter.convert(value);
		} catch(IllegalParameterException e) {
			throw e;
		} catch (Exception e) {
			throw new IllegalParameterException(value, e.getMessage());
		}
	}

	public <T> T get(String key, T defaultValue, Converter<T> converter) {
		String value = internalGet(key);
		try {
			return converter.convert(contains(key) ? internalGet(key) : defaultValue.toString());
		} catch(IllegalParameterException e) {
			throw e;
		} catch (Exception e) {
			throw new IllegalParameterException(value, e.getMessage());
		}
	}

	public <T> List<T> getList(String key, Converter<T> converter) {
		return get(key, new ListConverter<T>(converter));
	}

	public <T> List<T> getList(String key, List<T> defaultValue, Converter<T> converter) {
		return get(key, defaultValue, new ListConverter<T>(converter));
	}

	public String get(String key) {
		return get(key, Converter.STRING);
	}

	public String get(String key, String defaultValue) {
		return get(key, defaultValue, Converter.STRING);
	}

	public int getInt(String key) {
		return get(key, Converter.INTEGER);
	}

	public int getInt(String key, int defaultValue) {
		return get(key, defaultValue, Converter.INTEGER);
	}

	public long getLong(String key) {
		return get(key, Converter.LONG);
	}

	public long getLong(String key, long defaultValue) {
		return get(key, defaultValue, Converter.LONG);
	}

	public boolean getBool(String key) {
		return get(key, Converter.BOOLEAN);
	}

	public boolean getBool(String key, boolean defaultValue) {
		return get(key, defaultValue, Converter.BOOLEAN);
	}

	public long getTime(String key) {
		return get(key, new TimeAndUnitConverter()).getMillis();
	}

	public long getTime(String key, String defaultValue) {
		TimeAndUnitConverter converter = new TimeAndUnitConverter();
		return get(key, converter.convert(defaultValue), converter).getMillis();
	}

	public <T extends Enum<T>> T getEnum(String key, Class<T> clazz) {
		return get(key, new EnumConverter<T>(clazz));
	}

	public <T extends Enum<T>> T getEnum(String key, T defaultValue, Class<T> clazz) {
		return get(key, defaultValue, new EnumConverter<T>(clazz));
	}

	public URL getUrl(String key) {
		return get(key, new URLConverter());
	}

	public URL getUrl(String key, String defaultValue) {
		URLConverter converter = new URLConverter();
		return get(key, converter.convert(defaultValue), converter);
	}
}
