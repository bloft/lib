package dk.lbloft.config.converter;

import dk.lbloft.util.TimeAndUnit;

public class TimeAndUnitConverter extends Converter<TimeAndUnit> {
	@Override
	public TimeAndUnit convert(String data) {
		return TimeAndUnit.parse(data);
	}
}
