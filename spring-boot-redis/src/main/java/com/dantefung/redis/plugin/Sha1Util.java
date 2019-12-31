package com.dantefung.redis.plugin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class Sha1Util {
	private static final String ZERO = "0";
	private static final String ALGORITHM = "SHA1";

	private Sha1Util() {
		
	}
	/**
	 * sha1加密
	 * 
	 * @param str
	 * @return 返回十六进制的字符串形式，全部小写
	 */
	public static String encrypt(String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
			messageDigest.update(str.getBytes());
			return byte2hex(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			log.error("",e);
		}
		return null;
	}

	/**
	 * 二进制转十六进制字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append(ZERO);
			}
			sign.append(hex);
		}
		return sign.toString();
	}
}
