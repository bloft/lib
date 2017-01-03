package dk.lbloft.option;

import java.io.File;
import java.util.List;

class FileElement extends StringElement {
	private boolean mustExists;
	
	public FileElement(String name, String description, String defaultValue, boolean mustExists) {
		super(name, description, defaultValue);
		this.mustExists = mustExists;
	}
	
	@Override
	public String getValue(List<String> options) throws Exception {
		File value = new File(super.getValue(options));
		if(mustExists && !value.exists()) {
			throw new ElementException(this, "File dont exist");
		}
		return value.getAbsolutePath();
	}
}
