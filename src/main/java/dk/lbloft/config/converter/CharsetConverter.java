package dk.lbloft.config.converter;

import java.nio.charset.Charset;
import java.util.Iterator;

import dk.lbloft.config.IllegalParameterException;

public class CharsetConverter extends Converter<Charset> {

	@Override
	public Charset convert(String name) {
		Charset cs = Charset.forName(name);
		if(cs == null) {
			throw new IllegalParameterException(name, (Iterator<Object>) Charset.availableCharsets().keySet());
		}
		return cs;
	}

}
