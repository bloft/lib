package dk.lbloft.option;

import java.util.List;

class BooleanElement extends OptionElement {
	public BooleanElement(String name, String description) {
		super(name, description);
	}

	@Override
	public String getValue(List<String> options) {
		return options.contains(getOptionName()) ? "true" : "false";
	}
}
