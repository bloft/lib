package dk.lbloft.config.converter;

import java.util.ArrayList;
import java.util.List;

public class ListConverter<T> extends Converter<List<T>> {
	private final Converter<T> converter;

	public ListConverter(Converter<T> converter) {
		this.converter = converter;
	}

	@Override
	public List<T> convert(String value) {
		ArrayList<T> list = new ArrayList<>();
		for (String s : value.split(",")) {
			list.add(converter.convert(s));
		}
		return list;
	}

	@Override
	public String toString(List<T> values) {
		StringBuilder sb = new StringBuilder();
		for (T value : values) {
			if (sb.length() > 0) sb.append(",");
			sb.append(value);
		}
		return sb.toString();
	}
}