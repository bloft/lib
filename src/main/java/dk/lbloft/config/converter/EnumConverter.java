package dk.lbloft.config.converter;

import dk.lbloft.config.IllegalParameterException;

public class EnumConverter<T extends Enum<T>> extends Converter<T> {
	
	private Class<T> enumType;
	
	public EnumConverter(Class<T> enumType) {
		this.enumType = enumType;
	}

	@Override
	public T convert(String value) {
		try {
			return Enum.valueOf(enumType, value);
		} catch(IllegalArgumentException e) {
			throw new IllegalParameterException(value, enumType.getEnumConstants());
		}
	}
	
}
