package com.dantefung.core.crypto;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5工具类
 */
public class Md5Utils {

    /**
     * 工具类，构造方法私有
     */
    private Md5Utils() {

    }

    /**
     * md5 加密
     *
     * @param source 明文
     * @return 密文
     * @throws NoSuchAlgorithmException 不支持md5算法
     */
    public static String encrypt(String source) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] bytes;

        if (source != null) {
            bytes = md.digest(source.getBytes());
        } else {
            bytes = md.digest();
        }
        return HexUtils.byte2hex(bytes).toUpperCase();
    }

    /**
     * md5加密
     *
     * @param source 明文
     * @return 密文
     * @throws NoSuchAlgorithmException 不支持md5算法
     */
    public static String encryptLowerCase(String source) throws NoSuchAlgorithmException {
        return encrypt(source).toLowerCase();
    }

    /**
     * 判断明文和密文是否对应
     *
     * @param source    明文
     * @param encrypted 密文
     * @return 是否相等
     * @throws NoSuchAlgorithmException 不支持md5算法
     */
    public static boolean verify(String source, String encrypted) throws NoSuchAlgorithmException {
        return encrypt(source).equalsIgnoreCase(encrypted);
    }

}