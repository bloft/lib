package dk.lbloft.util;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.common.base.Charsets;


public class Tlv extends HashMap<Byte, byte[]> {
	private static final Logger logger = Logger.getLogger(Tlv.class.getName());

	public Tlv put(Enum<?> key, byte[] value) {
		put((byte)key.ordinal(), value);
		return this;
	}

	public Tlv put(Enum<?> key, String value) {
		return put(key, value.getBytes(Charsets.UTF_8));
	}

	public Tlv put(Enum<?> key, long value) {
		return put(key, ByteBuffer.allocate(8).putLong(value).array());
	}

	public Tlv put(Enum<?> key, int value) {
		return put(key, ByteBuffer.allocate(4).putInt(value).array());
	}

	public Tlv put(Enum<?> key, byte value) {
		return put(key, ByteBuffer.allocate(1).put(value).array());
	}

	public Tlv put(Enum<?> key, char value) {
		return put(key, ByteBuffer.allocate(2).putChar(value).array());
	}

	public Tlv put(Enum<?> key, Enum<?> value) {
		return put(key, new byte[] { (byte)value.ordinal() });
	}


	public boolean containsKey(Enum<?> key) {
		return containsKey((byte)key.ordinal());
	}

	public byte[] get(Enum<?> key) {
		return get((byte)key.ordinal());
	}

	public String getString(Enum<?> key) {
		return new String(get(key), Charsets.UTF_8);
	}

	public long getLong(Enum<?> key) {
		return ByteBuffer.wrap(get(key)).getLong();
	}

	public int getInt(Enum<?> key) {
		return ByteBuffer.wrap(get(key)).getInt();
	}

	public Byte getByte(Enum<?> key) {
		byte[] bytes = get(key);
		if(bytes != null && bytes.length == 1) {
			return bytes[0];
		}
		return null;
	}

	public char getChar(Enum<?> key) {
		return ByteBuffer.wrap(get(key)).getChar();
	}

	public <T extends Enum<T>> T getEnum(Enum<?> key, Class<T> enumType) {
		Byte ordinal = getByte(key);
		if(ordinal != null) {
			for (T e : enumType.getEnumConstants()) {
				if (e.ordinal() == ordinal) {
					return e;
				}
			}
		}
		return null;
	}

	public static Tlv parse(byte[] data) {
		Tlv klv = new Tlv();
		int pos = 0;
		while(pos < data.length) {
			byte key = data[pos++];
			int length = 0;
			int count = 0;
			do {
				length |= (data[pos] & 0x7f) << (count++ * 7);
			} while ((0x80 & data[pos++]) != 0);
			klv.put(key, Arrays.copyOfRange(data, pos, pos+length));
			pos += length;
		}
		return klv;
	}

	public byte[] getBytes() {
		ByteArrayOutputStream joined = new ByteArrayOutputStream();
		try {
			for (Map.Entry<Byte, byte[]> entry : entrySet()) {
				joined.write(entry.getKey());
				int length = entry.getValue().length;
				while(length > 127) {
					joined.write((byte)(0x80 | length & 0x7f));
					length = length >>> 7;
				}
				joined.write((byte)(length & 0x7f));
				joined.write(entry.getValue());
			}
			return joined.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("Unable to convert to bytes", e);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (Map.Entry<Byte, byte[]> entry : entrySet()) {
			sb.append(entry.getKey());
			sb.append("=[");
			for (byte b : entry.getValue()) {
				sb.append(b);
				sb.append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("],");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("}");
		return sb.toString();
	}
}