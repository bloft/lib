package dk.lbloft.util;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import dk.lbloft.config.IllegalParameterException;

public class TimeAndUnit {
	private TimeUnit unit = TimeUnit.MILLISECONDS;
	private long time;
	
	private static BiMap<String, TimeUnit> units = HashBiMap.create();
	static {
		units.put("ms", TimeUnit.MILLISECONDS);
		units.put("s",  TimeUnit.SECONDS);
		units.put("m",  TimeUnit.MINUTES);
		units.put("h",  TimeUnit.HOURS);
		units.put("d",  TimeUnit.DAYS);
	}

	public TimeAndUnit(long time) {
		this(time, TimeUnit.MILLISECONDS);
	}
	
	public TimeAndUnit(long time, TimeUnit unit) {
		this.setTime(time);
		this.setUnit(unit);
	}

	public TimeUnit getUnit() {
		return unit;
	}

	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	public long getMillis() {
		return unit.toMillis(time);
	}
	
	public long getSecs() {
		return unit.toSeconds(time);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		long tmp = getMillis();
		tmp = toStringHandler(sb, tmp, TimeUnit.DAYS);
		tmp = toStringHandler(sb, tmp, TimeUnit.HOURS);
		tmp = toStringHandler(sb, tmp, TimeUnit.MINUTES);
		tmp = toStringHandler(sb, tmp, TimeUnit.SECONDS);
		tmp = toStringHandler(sb, tmp, TimeUnit.MILLISECONDS);
		if(sb.length() == 0) {
			sb.append("0ms");
		}
		return sb.toString().trim();
	}
	
	private long toStringHandler(StringBuilder sb, long time, TimeUnit unit) {
		long tmp = unit.convert(time, TimeUnit.MILLISECONDS);
		if(tmp > 0) {
			sb.append(tmp);
			sb.append(units.inverse().get(unit));
			sb.append(" ");
			time -= unit.toMillis(tmp);
		}
		return time;
	}
	
	public static TimeAndUnit parse(String timeAndUnit) {
		checkNotNull(timeAndUnit, "Values must not be null");
		checkArgument(!timeAndUnit.trim().equals(""), "Values must not be empty");
		
		Pattern pattern = Pattern.compile("([0-9]+)[ ]*([a-zA-Z]*)");
		Matcher matcher = pattern.matcher(timeAndUnit);
		
		long millies = 0;
		while (matcher.find()) {
			String unit = matcher.group(2).equals("") ? "ms" : matcher.group(2);
			if(units.containsKey(unit)) {
				try {
					millies += units.get(unit).toMillis(Long.parseLong(matcher.group(1)));
				} catch(Exception e) {
					throw new IllegalParameterException(matcher.group(0), "Unable to parse time");
				}
			} else {
				throw new IllegalParameterException(matcher.group(0), "Invalid unit");
			}
		}
		
		if(!matcher.replaceAll("").trim().equals("")) {
			throw new IllegalParameterException(matcher.replaceAll("").trim(), "Invalid char(s) in string");
		}
		
		return new TimeAndUnit(millies, TimeUnit.MILLISECONDS);
	}
}
