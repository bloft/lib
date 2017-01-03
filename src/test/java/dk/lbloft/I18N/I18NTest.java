package dk.lbloft.I18N;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class I18NTest {
	@Test
	public void testGetMyNameIs() {
		SomeTexts texts = I18NFactory.getProxy(SomeTexts.class);

		assertThat(texts.simple(), is("Simple"));

		assertThat(texts.myNameIs("John Doe"), is("My name is John Doe"));
		assertThat(texts.myNameIs("Jane Doe"), is("My name is Jane Doe"));
	}
}