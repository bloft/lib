package dk.lbloft.config;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import com.google.common.collect.Lists;

public abstract class HandleException {
	public abstract void handle() throws Exception;
	
	private List<Class<? extends Exception>> exceptions = Lists.newArrayList();
	
	public final void run() {
		try {
			handle();
			fail("Expected exception not found");
		} catch(Exception e) {
			if(exceptions.contains(e.getClass())) {
				assertTrue(true);
			} else {
				fail("Was not expecting this exception: " + e.getClass());
			}
		}
	}
	
	public final HandleException expect(Class<? extends Exception> exceptionClass) {
		exceptions.add(exceptionClass);
		return this;
	}
}
