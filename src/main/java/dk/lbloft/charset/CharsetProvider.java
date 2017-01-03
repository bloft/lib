package dk.lbloft.charset;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

public class CharsetProvider extends java.nio.charset.spi.CharsetProvider {
	
	private static ArrayList<Charset> charsets = new ArrayList<Charset>();
	
	static {
		charsets.add(new Gsm7BitCharset("Gsm7Bit", new String[] {"GSM_03.38"}));
	}

	@Override
	public Iterator<Charset> charsets() {
		return charsets.iterator();
	}

	@Override
	public Charset charsetForName(String charsetName) {
		for (Iterator<Charset> iterator = charsets(); iterator.hasNext();) {
			Charset cs = (Charset) iterator.next();
			if(cs.name().equalsIgnoreCase(charsetName)) {
				return cs;
			} else {
				for (String alias : cs.aliases()) {
					if(alias.equalsIgnoreCase(charsetName)) {
						return cs;
					}
				}
			}
		}
		return null;
	}

}
