package dk.lbloft.config.converter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import dk.lbloft.config.IllegalParameterException;

public class URLConverter extends Converter<URL> {

	@Override
	public URL convert(String value) {
		try {
			return new URL(value);
		} catch (Exception e) {
			File f = new File(value);
			if(f.exists()) {
				try {
					return f.toURI().toURL();
				} catch (MalformedURLException e1) {}
			}
			throw new IllegalParameterException(value, e.getMessage());
		}
	}

}
