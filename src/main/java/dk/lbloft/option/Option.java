package dk.lbloft.option;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import dk.lbloft.config.Configuration;
import dk.lbloft.config.impl.PropertiesConfiguration;

public class Option {
	private HashSet<OptionElement> elements = new HashSet<OptionElement>();
	private String configPrefix = "cli.option.";
	
	public Option setPrefix(String prefix) {
		configPrefix = prefix;
		return this;
	}

	public Option addString(String name, String description) {
		elements.add(new StringElement(name, description));
		return this;
	}

	public Option addString(String name, String description, String defaultValue) {
		elements.add(new StringElement(name, description, defaultValue));
		return this;
	}
	
	public Option addFile(String name, String description) {
		elements.add(new FileElement(name, description, null, false));
		return this;
	}
	
	public Option addFile(String name, String description, String defaultValue) {
		elements.add(new FileElement(name, description, defaultValue, false));
		return this;
	}
	
	public Option addExistingFile(String name, String description) {
		elements.add(new FileElement(name, description, null, true));
		return this;
	}
	
	public Option addExistingFile(String name, String description, String defaultValue) {
		elements.add(new FileElement(name, description, defaultValue, true));
		return this;
	}
	
	public Option addBoolean(String name, String description) {
		elements.add(new BooleanElement(name, description));
		return this;
	}

	public Configuration parse(String... options) {
		Properties props = new Properties();
		try {
			List<String> list = Arrays.asList(options);
			elements.add(new HelpElement());
			for (OptionElement element : elements) {
				props.setProperty(configPrefix + element.getName(), element.getValue(list));
			}
		} catch(Exception e) {
			printUsage(e);
		}
		return new PropertiesConfiguration(props);
	}

	private void printUsage(Exception e) {
		StringBuilder sb = new StringBuilder();
		sb.append("Usage: {application} ");
		for (OptionElement element : elements) {
			if(!element.isOptional()) {
				sb.append(element.getOptionName());
				sb.append(" {} ");
			}
		}
		System.out.println(sb.toString());
		if(e != null) {
			System.out.println("------------------------------");
			System.out.println("Error:");
			System.out.println(e);
			System.out.println();
		}
		System.out.println("------------------------------");
		System.out.println("Options:");
		for (OptionElement element : elements) {
			System.out.println(element);
		}
		System.exit(1);
	}
	
	private class HelpElement extends BooleanElement {
		public HelpElement() {
			super("help", "Print this help message");
		}
		
		@Override
		public String getValue(List<String> options) {
			if(Boolean.parseBoolean(super.getValue(options))) {
				printUsage(null);
			}
			return "false";
		}
	}
}