package com.dantefung.core.crypto;

/**
 * 16进制工具类
 */

public class HexUtils {

    /**
     * 工具类，私有构造方法
     */
    private HexUtils() {

    }


    /**
     * byte转16进制字符串
     *
     * @param bytes byte数组
     * @return 16进制字符串
     */
    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
}