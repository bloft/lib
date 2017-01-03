package dk.lbloft.encryption;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.io.BaseEncoding;

public class Encrypter {
	static {
		// Set default key
		setKey("%x&(v%GFD%#cguA#");
	}

//	private static IvParameterSpec iv = new IvParameterSpec((new SecureRandom()).generateSeed(16));
	private static IvParameterSpec iv = new IvParameterSpec(new byte[16]);
	
	private static byte[] key;
	
	public static void setKey(String keyString) {
		key = keyString.getBytes();
		for (byte i = 0;i < key.length;i+=2) {
			key[i] = (byte) ((i*12345) % 256);
		}
	}
	
	public static Cipher getCipher(int mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		SecretKeySpec keySpec = new SecretKeySpec(key,"AES".split("/")[0]);
		Cipher crypter =  Cipher.getInstance("AES/CBC/PKCS5Padding");
		crypter.init(mode, keySpec, iv);
		return crypter;
	}
	
	public static byte[] encrypt(byte[] data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		return getCipher(Cipher.ENCRYPT_MODE).doFinal(data);
	}
	
	public static byte[] decrypt(byte[] data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		return getCipher(Cipher.DECRYPT_MODE).doFinal(data);
	}
	
	public static String encryptString(String input) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		return BaseEncoding.base64().encode(encrypt(input.getBytes()));
	}
	
	public static String decryptString(String input) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		return new String(decrypt(BaseEncoding.base64().decode(input)));
	}
	
	public static CipherInputStream getEncryptedInputStream(InputStream stream) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		return new CipherInputStream(stream, getCipher(Cipher.DECRYPT_MODE));
	}
	
	public static CipherOutputStream getEncryptedOutputStream(OutputStream stream) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		return new CipherOutputStream(stream, getCipher(Cipher.ENCRYPT_MODE));
	}
}
