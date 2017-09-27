package dk.lbloft.util;

import static org.hamcrest.Matchers.*;
import java.io.IOException;
import java.util.logging.Logger;

import org.junit.Test;

import static org.junit.Assert.assertThat;


public class KLVTest {
	private static final Logger logger = Logger.getLogger(KLVTest.class.getName());

	@Test
	public void put() throws IOException {
		assertThat(new Tlv().put(Day.MONDAY, "test").getBytes(), equalTo(new byte[] {0, 4, 116, 101, 115, 116}));
		assertThat(new Tlv().put(Day.TUESDAY, "abcdefgh").getBytes(), equalTo(new byte[] {1, 8, 97, 98, 99, 100, 101, 102, 103, 104}));
		assertThat(new Tlv().put(Day.WEDNESDAY, "test").getBytes(), equalTo(new byte[] {2, 4, 116, 101, 115, 116}));
		assertThat(new Tlv().put(Day.MONDAY, 'a').getBytes(), equalTo(new byte[] {0, 2, 0, 97}));
		assertThat(new Tlv().put(Day.SUNDAY, 'Ͻ').getBytes(), equalTo(new byte[] {6, 2, 3, -3}));
		assertThat(new Tlv().put(Day.MONDAY, (byte)1).getBytes(), equalTo(new byte[] {0, 1, 1}));
		assertThat(new Tlv().put(Day.MONDAY, 1).getBytes(), equalTo(new byte[] {0, 4, 0, 0, 0, 1}));
		assertThat(new Tlv().put(Day.MONDAY, 1l).getBytes(), equalTo(new byte[] {0, 8, 0, 0, 0, 0, 0, 0, 0, 1}));
		assertThat(new Tlv().put(Day.WEDNESDAY, 1234567890).getBytes(), equalTo(new byte[] {2, 4, 73, -106, 2, -46}));
	}

	@Test
	public void parse() throws IOException {
		assertThat(Tlv.parse(new byte[] {1, 1, 1}).getBytes(), equalTo(new byte[] {1, 1, 1}));
		assertThat(Tlv.parse(new byte[] {1, 1, 1}).get((byte)1), equalTo(new byte[] {1}));
		assertThat(Tlv.parse(new byte[] {1, 1, 1, 2, 1, 2}).getBytes(), equalTo(new byte[] {1, 1, 1, 2, 1, 2}));
		assertThat(Tlv.parse(new byte[] {1, 1, 1, 2, 1, 2}).get((byte)1), equalTo(new byte[] {1}));
		assertThat(Tlv.parse(new byte[] {1, 1, 1, 2, 1, 2}).get((byte)2), equalTo(new byte[] {2}));
	}

	@Test
	public void get() {
		assertThat(Tlv.parse(new byte[] {1, 1, 1}).get(Day.TUESDAY), equalTo(new byte[] {1}));
		assertThat(Tlv.parse(new byte[] {1, 1, 1}).get(Day.MONDAY), equalTo(null));
		assertThat(Tlv.parse(new byte[] {1, 1, 1}).getEnum(Day.TUESDAY, Day.class), equalTo(Day.TUESDAY));
		assertThat(Tlv.parse(new byte[] {1, 1, 1}).getEnum(Day.MONDAY, Day.class), equalTo(null));
		assertThat(Tlv.parse(new byte[] {0, 4, 116, 101, 115, 116}).getString(Day.MONDAY), equalTo("test"));
	}

	public enum Day {
		MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
	}

	public static void main(String[] args) throws IOException {
		Tlv klv = new Tlv();
		klv.put(Day.MONDAY, "Mandag");
		klv.put(Day.TUESDAY, "Tirsdag");
		System.out.println(klv);

		Tlv tmp = Tlv.parse(klv.getBytes());
		System.out.println(tmp.getString(Day.MONDAY));
		System.out.println(tmp.getString(Day.TUESDAY));
	}

}