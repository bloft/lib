package dk.lbloft.I18N;


/**
 * Some comment about this classes usage
 */
public interface SomeTexts {

	/**
	 * Descriptive javadoc
	 */
	@Message("Simple")
	String simple();

	/**
	 * Some more description
	 * @Arg name Description of the parameter
	 */
	@Message("My name is ${name}")
	String myNameIs(@Arg("name") String name);

}