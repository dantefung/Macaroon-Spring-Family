package com.dantefung.core.crypto;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 * 加密工具类
 */
class CipherUtils {

    /**
     * 默认编码
     */
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 工具类，私有构造方法
     */
    private CipherUtils() {
    }

    /**
     * 加密字符串
     *
     * @param plainText 明文
     * @param keySize   key大小
     * @param key       加密key
     * @param method    方法
     * @return 密文
     */
    static String encrypt(String method, String plainText, int keySize, String key) {
        if (null == plainText) {
            return null;
        }
        try {
            // 对Cipher完成加密或解密工作
            Cipher cipher = Cipher.getInstance(method);
            // 对Cipher初始化,加密模式
            cipher.init(Cipher.ENCRYPT_MODE, getKey(method, keySize, key));
            // 加密数据
            return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes(DEFAULT_CHARSET)));
        } catch (InvalidKeyException | NoSuchAlgorithmException
                | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密字符串
     *
     * @param cipherText 密文
     * @param keySize    key大小
     * @param key        加密key
     * @param method     方法
     * @return 明文
     */
    static String decrypt(String method, String cipherText, int keySize, String key) {
        if (null == cipherText) {
            return null;
        }
        try {
            // 对Cipher初始化,加密模式
            Cipher cipher = Cipher.getInstance(method);
            // 对Cipher初始化,加密模式
            cipher.init(Cipher.DECRYPT_MODE, getKey(method, keySize, key));
            // 解密数据
            return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)), DEFAULT_CHARSET);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取key
     *
     * @param method  方法
     * @param keySize 键大小
     * @param keySeed key种子
     * @return 秘钥
     */
    private static SecretKey getKey(String method, int keySize, String keySeed) {
        byte[] keyBytes = Arrays.copyOf(keySeed.getBytes(StandardCharsets.US_ASCII), keySize / 8);
        return new SecretKeySpec(keyBytes, method);
    }
}