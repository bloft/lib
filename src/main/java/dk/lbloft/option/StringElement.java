package dk.lbloft.option;

import java.util.List;

class StringElement extends OptionElement {
	String defaultValue;
	
	public StringElement(String name, String description) {
		this(name, description, null);
	}
	
	@Override
	public String getValue(List<String> options) throws Exception {
		int index = options.indexOf(getOptionName());
		if(index == -1) {
			if(defaultValue == null) {
				throw new ElementException(this, "Value not defined");
			} else {
				return defaultValue;
			}
		} else {
			return options.get(index + 1);
		}
	}
	
	@Override
	public boolean isOptional() {
		return defaultValue != null;
	}
	
	public StringElement(String name, String description, String defaultValue) {
		super(name, description);
		this.defaultValue = defaultValue;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		if(defaultValue != null) {
			sb.append(" [ ");
			sb.append(defaultValue);
			sb.append(" ]");
		}
		return sb.toString();
	}
}
