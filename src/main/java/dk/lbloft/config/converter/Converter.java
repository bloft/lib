package dk.lbloft.config.converter;

import dk.lbloft.config.IllegalParameterException;

public abstract class Converter<T> {
	public abstract T convert(String value);

	public String toString(T value) {
		return value.toString();
	}

	public static final Converter<String> STRING = new Converter<String>() {
		@Override
		public String convert(String value) {
			return value;
		}
	};
	public static final Converter<Byte> BYTE = new Converter<Byte>() {
		@Override
		public Byte convert(String value) {
			return Byte.valueOf(value);
		}
	};
	public static final Converter<Character> CHAR = new Converter<Character>() {
		@Override
		public Character convert(String value) {
			if(value.length() == 1) {
				return value.charAt(0);
			} else {
				throw new IllegalParameterException(value, "Length must be 1");
			}
		}
	};
	public static final Converter<Integer> INTEGER = new Converter<Integer>() {
		@Override
		public Integer convert(String value) {
			return Integer.valueOf(value);
		}
	};
	public static final Converter<Long> LONG = new Converter<Long>() {
		@Override
		public Long convert(String value) {
			return Long.valueOf(value);
		}
	};
	public static final Converter<Float> FLOAT = new Converter<Float>() {
		@Override
		public Float convert(String value) {
			return Float.valueOf(value);
		}
	};
	public static final Converter<Double> DOUBLE = new Converter<Double>() {
		@Override
		public Double convert(String value) {
			return Double.valueOf(value);
		}
	};
	public static final Converter<Boolean> BOOLEAN = new Converter<Boolean>() {
		@Override
		public Boolean convert(String value) {
			return Boolean.valueOf(value);
		}
	};
}
