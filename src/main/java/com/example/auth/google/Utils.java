package com.example.auth.google;

import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

import de.taimos.totp.TOTP;

public abstract class Utils {

	public static String generateSecretKey() {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		Base32 base32 = new Base32();
		return base32.encodeToString(bytes);
	}

	public static String getTOTPCode(String secretKey) {
		Base32 base32 = new Base32();
		byte[] bytes = base32.decode(secretKey);
		String hexKey = Hex.encodeHexString(bytes);
		return TOTP.getOTP(hexKey);
	}

	/**
	 * 
	 * @param appCode
	 * @param userSecretKey
	 * @return
	 */
	public static boolean isCodeValid(String appCode, String userSecretKey) {

		if (userSecretKey == null) {
			throw new IllegalArgumentException("secret key cannot be null");
		}

		String totpCode = getTOTPCode(userSecretKey);

		return StringUtils.equals(appCode, totpCode);
	}

}
