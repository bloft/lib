package dk.lbloft.config;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import dk.lbloft.util.TimeAndUnit;

public class TimeAndUnitTest {

	@Test
	public void parse() {
		assertThat(TimeAndUnit.parse("10").getMillis(), equalTo(10l));
		assertThat(TimeAndUnit.parse("10ms").getMillis(), equalTo(10l));
		assertThat(TimeAndUnit.parse("10s").getMillis(), equalTo(10000l));
		assertThat(TimeAndUnit.parse("10m").getMillis(), equalTo(600000l));
		assertThat(TimeAndUnit.parse("10h").getMillis(), equalTo(36000000l));
		assertThat(TimeAndUnit.parse("10d").getMillis(), equalTo(864000000l));
		assertThat(TimeAndUnit.parse("10s 10ms").getMillis(), equalTo(10010l));
		assertThat(TimeAndUnit.parse("10 s").getMillis(), equalTo(10000l));
		assertThat(TimeAndUnit.parse("10 s 10").getMillis(), equalTo(10010l));


		try {
			TimeAndUnit.parse("10dsa");
			fail("Expected exception");
		} catch (Exception e) {
			assertTrue(true);
		}
		
		try {
			TimeAndUnit.parse("10d a");
			fail("Expected exception");
		} catch (Exception e) {
			assertTrue(true);
		}
		
		try {
			TimeAndUnit.parse("");
			fail("Expected exception");
		} catch (Exception e) {
			assertTrue(true);
		}
		
		try {
			TimeAndUnit.parse(null);
			fail("Expected exception");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
}
