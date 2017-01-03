package dk.lbloft.option;

import java.util.List;

abstract class OptionElement {
	String name;
	String description;

	public OptionElement(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract String getValue(List<String> options) throws Exception;
	
	public boolean isOptional() {
		return true;
	}
	
	public String getOptionName() {
		return "-" + (name.length() > 1 ? "-" : "") + name;
	}

	@Override
	public String toString() {
		return String.format("%15s : %s", getOptionName(), description);
//		return String.format(" %2s%-20s %s", "-" + (name.length() > 1 ? "-" : ""), name, description);
	}
}
