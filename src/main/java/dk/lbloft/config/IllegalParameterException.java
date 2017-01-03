package dk.lbloft.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import com.google.common.base.Joiner;

public final class IllegalParameterException extends RuntimeException {
	private static final long serialVersionUID = 2917360187780147358L;
	
	public IllegalParameterException(String value) {
		super("Invalid value '" + value + "'");
	}
	
	public IllegalParameterException(String value, String message) {
		super("Invalid value '" + value + "', " + message);
	}
	
	public IllegalParameterException(String value, Object[] values) {
		this(value, Arrays.asList(values));
	}

	public IllegalParameterException(String value, Iterable<Object> values) {
		this(value, values.iterator());
	}

	public IllegalParameterException(String value, Iterator<Object> values) {
		super("Value '" + value + "' must be one of '" + Joiner.on("', '").join(values) + "'");
	}
}
