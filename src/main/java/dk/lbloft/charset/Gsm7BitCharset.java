package dk.lbloft.charset;

import java.nio.ByteBuffer;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.HashMap;

/**
 * Java charset for encoding and decoding GSM 03.38 charset
 * 
 * based on http://www.unicode.org/Public/MAPPINGS/ETSI/GSM0338.TXT
 * @author Bjarne Loft
 */
public class Gsm7BitCharset extends Charset {
	
	private static final int[][] mapping = {
		{ 0x0040, 0x00 }, { 0x00A3, 0x01 }, { 0x0024, 0x02 }, { 0x00A5, 0x03 }, { 0x00E8, 0x04 }, { 0x00E9, 0x05 }, 
		{ 0x00F9, 0x06 }, { 0x00EC, 0x07 }, { 0x00F2, 0x08 }, { 0x00C7, 0x09 }, { 0x000A, 0x0a }, { 0x00D8, 0x0b }, 
		{ 0x00F8, 0x0c }, { 0x000D, 0x0d }, { 0x00C5, 0x0e }, { 0x00E5, 0x0f }, { 0x0394, 0x10 }, { 0x005F, 0x11 }, 
		{ 0x03A6, 0x12 }, { 0x0393, 0x13 }, { 0x039B, 0x14 }, { 0x03A9, 0x15 }, { 0x03A0, 0x16 }, { 0x03A8, 0x17 }, 
		{ 0x03A3, 0x18 }, { 0x0398, 0x19 }, { 0x039E, 0x1a }, { -1,     0x1b }, { 0x00C6, 0x1c }, { 0x00E6, 0x1d }, 
		{ 0x00DF, 0x1e }, { 0x00C9, 0x1f }, { 0x0020, 0x20 }, { 0x0021, 0x21 }, { 0x0022, 0x22 }, { 0x0023, 0x23 }, 
		{ 0x00A4, 0x24 }, { 0x0025, 0x25 }, { 0x0026, 0x26 }, { 0x0027, 0x27 }, { 0x0028, 0x28 }, { 0x0029, 0x29 }, 
		{ 0x002A, 0x2a }, { 0x002B, 0x2b }, { 0x002C, 0x2c }, { 0x002D, 0x2d }, { 0x002E, 0x2e }, { 0x002F, 0x2f }, 
		{ 0x0030, 0x30 }, { 0x0031, 0x31 }, { 0x0032, 0x32 }, { 0x0033, 0x33 }, { 0x0034, 0x34 }, { 0x0035, 0x35 }, 
		{ 0x0036, 0x36 }, { 0x0037, 0x37 }, { 0x0038, 0x38 }, { 0x0039, 0x39 }, { 0x003A, 0x3a }, { 0x003B, 0x3b }, 
		{ 0x003C, 0x3c }, { 0x003D, 0x3d }, { 0x003E, 0x3e }, { 0x003F, 0x3f }, { 0x00A1, 0x40 }, { 0x0041, 0x41 }, 
		{ 0x0042, 0x42 }, { 0x0043, 0x43 }, { 0x0044, 0x44 }, { 0x0045, 0x45 }, { 0x0046, 0x46 }, { 0x0047, 0x47 }, 
		{ 0x0048, 0x48 }, { 0x0049, 0x49 }, { 0x004A, 0x4a }, { 0x004B, 0x4b }, { 0x004C, 0x4c }, { 0x004D, 0x4d }, 
		{ 0x004E, 0x4e }, { 0x004F, 0x4f }, { 0x0050, 0x50 }, { 0x0051, 0x51 }, { 0x0052, 0x52 }, { 0x0053, 0x53 }, 
		{ 0x0054, 0x54 }, { 0x0055, 0x55 }, { 0x0056, 0x56 }, { 0x0057, 0x57 }, { 0x0058, 0x58 }, { 0x0059, 0x59 }, 
		{ 0x005A, 0x5a }, { 0x00C4, 0x5b }, { 0x00D6, 0x5c }, { 0x00D1, 0x5d }, { 0x00DC, 0x5e }, { 0x00A7, 0x5f }, 
		{ 0x00BF, 0x60 }, { 0x0061, 0x61 }, { 0x0062, 0x62 }, { 0x0063, 0x63 }, { 0x0064, 0x64 }, { 0x0065, 0x65 }, 
		{ 0x0066, 0x66 }, { 0x0067, 0x67 }, { 0x0068, 0x68 }, { 0x0069, 0x69 }, { 0x006A, 0x6a }, { 0x006B, 0x6b }, 
		{ 0x006C, 0x6c }, { 0x006D, 0x6d }, { 0x006E, 0x6e }, { 0x006F, 0x6f }, { 0x0070, 0x70 }, { 0x0071, 0x71 }, 
		{ 0x0072, 0x72 }, { 0x0073, 0x73 }, { 0x0074, 0x74 }, { 0x0075, 0x75 }, { 0x0076, 0x76 }, { 0x0077, 0x77 }, 
		{ 0x0078, 0x78 }, { 0x0079, 0x79 }, { 0x007A, 0x7a }, { 0x00E4, 0x7b }, { 0x00F6, 0x7c }, { 0x00F1, 0x7d }, 
		{ 0x00FC, 0x7e }, { 0x00E0, 0x7f }
	};
	
	private static final int[][] mappingExtra = {
		{ 0x000C, 0x0A }, { 0x005E, 0x14 }, { 0x007B, 0x28 }, { 0x007D, 0x29 }, { 0x005C, 0x2F }, { 0x005B, 0x3C }, 
		{ 0x007E, 0x3D }, { 0x005D, 0x3E }, { 0x007C, 0x40 }, { 0x20AC, 0x65 }
	};
	
	private static byte ESCAPE_CHAR = 0x1b;
	private static byte UNKNOWN_CHAR = 0x3f; // Use ? as unknown char
	
	protected static HashMap<Integer, Byte> encodeMap = new HashMap<Integer, Byte>();
	protected static HashMap<Integer, Byte> encodeExtraMap = new HashMap<Integer, Byte>();
	
	protected static HashMap<Byte, Character> decodeMap = new HashMap<Byte, Character>();
	protected static HashMap<Byte, Character> decodeExtraMap = new HashMap<Byte, Character>();
	
	static {
		for (int i = 0; i < mapping.length; i++) {
			encodeMap.put(mapping[i][0], (byte)mapping[i][1]);
			decodeMap.put((byte)mapping[i][1], (char)mapping[i][0]);
		}
		for (int i = 0; i < mappingExtra.length; i++) {
			encodeExtraMap.put(mappingExtra[i][0], (byte)mappingExtra[i][1]);
			decodeExtraMap.put((byte)mappingExtra[i][1], (char)mappingExtra[i][0]);
		}
	}

	protected Gsm7BitCharset(String canonicalName, String[] aliases) {
		super(canonicalName, aliases);
	}

	@Override
	public boolean contains(Charset cs) {
		return false;
	}

	// Byte to Unicode
	@Override
	public CharsetDecoder newDecoder() { 
		return new CharsetDecoder(this, 1, 2) {
			
			@Override
			protected CoderResult decodeLoop(ByteBuffer in, CharBuffer out) {
				byte lastByte = -1;
				while(in.hasRemaining() && out.hasRemaining()) {
					byte b = in.get();
					if(b == ESCAPE_CHAR) {
					} else if(lastByte == ESCAPE_CHAR && decodeExtraMap.containsKey(b)) {
						out.put((char)decodeExtraMap.get(b));
					} else if(decodeMap.containsKey(b)) {
						out.put((char)decodeMap.get(b));
					} else {
						return CoderResult.unmappableForLength(1);
					}
					lastByte = b;
				}
				return CoderResult.UNDERFLOW;
			}
			
		};
	}

	// Unicode to byte
	@Override
	public CharsetEncoder newEncoder() {
		return new CharsetEncoder(this, 1, 2) {

			@Override
			protected CoderResult encodeLoop(CharBuffer in, ByteBuffer out) {
				while(in.hasRemaining() && out.hasRemaining()) {
					int ch = in.get();
					if(encodeMap.containsKey(ch)) {
						out.put(encodeMap.get(ch));
					} else if (encodeExtraMap.containsKey(ch)) {
						if(out.remaining() < 2) {
							in.position(in.position()-1);
							return CoderResult.OVERFLOW;
						}
						out.put((byte) ESCAPE_CHAR);
						out.put(encodeExtraMap.get(ch));
					} else {
						out.put(UNKNOWN_CHAR); // Add UNKNOWN_CHAR when a char is not recognised
					}
				}
				return CoderResult.UNDERFLOW;
			}
			
		};
	}
	
}
