package dk.lbloft.service;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import dk.lbloft.config.Configuration;
import dk.lbloft.locate.Locator;

public abstract class BaseCo {
	@XStreamAsAttribute
	private String name;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	protected String getConfigName() {
		if(name != null) {
			return name;
		} else if(getClass().isAnnotationPresent(XStreamAlias.class)) {
			return getClass().getAnnotation(XStreamAlias.class).value();
		} else {
			return this.getClass().getSimpleName();
		}
	}
	
	public Configuration getConfig() {
		try {
			return Locator.locate(Configuration.class).getSubSection(getConfigName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unable to locate Configuration", e);
		}
	}
}
