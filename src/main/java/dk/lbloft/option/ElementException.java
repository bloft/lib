package dk.lbloft.option;

class ElementException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private OptionElement element;
	
	public ElementException(OptionElement element, String message) {
		super(message);
		this.element = element;
	}
	
	@Override
	public String toString() {
		return String.format("%15s : %s", element.getOptionName(), getMessage());
	}

}
