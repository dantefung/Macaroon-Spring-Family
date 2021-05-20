package com.dantefung.core.crypto;

/**
 * DES加密算法
 */
public class DesUtils {

    /**
     * DES
     */
    private static final String KEY_DES = "DES";

    /**
     * KEY长度
     */
    private static final int KEY_SIZE = 64;

    /**
     * 工具类，私有构造
     */
    private DesUtils() {
    }

    /**
     * 加密字符串
     *
     * @param plainText 明文
     * @param key       加密key
     * @return 密文
     */
    public static String encrypt(String plainText, String key) {
        return CipherUtils.encrypt(KEY_DES, plainText, KEY_SIZE, key);
    }

    /**
     * 解密字符串
     *
     * @param cipherText 密文
     * @param key        加密key
     * @return 明文
     */
    public static String decrypt(String cipherText, String key) {
        return CipherUtils.decrypt(KEY_DES, cipherText, KEY_SIZE, key);
    }

}