package com.dantefung.core.crypto;


import com.dantefung.core.lang.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 签名工具类
 */
public class SignUtils {

    /**
     * 参数sign
     */
    private static final String PARAM_SIGN = "sign";

    /**
     * 工具类，私有构造方法
     */
    private SignUtils() {

    }

    /**
     * 签名
     *
     * @param params 参数map
     * @param secret 密钥
     * @return 签名后的字符串
     */
    public static String sign(Map<String, String> params, String secret) {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        query.append(secret);
        for (String key : keys) {
            String value = params.get(key);
            if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
                query.append(key).append(value);
            }
        }

        // 第三步：使用MD5/HMAC加密
        byte[] bytes;

        query.append(secret);
        bytes = encryptMd5(query.toString());
        // 第四步：把二进制转化为大写的十六进制
        return HexUtils.byte2hex(bytes);
    }

    /**
     * 校验
     *
     * @param params 参数map
     * @param secret 密钥
     * @return 校验结果
     */
    public static boolean verify(Map<String, String> params, String secret) {
        String sign = params.get("sign");
        if (StringUtils.isBlank(sign)) {
            return false;
        }
        Map<String, String> checkParams = new HashMap<>(params.size());
        params.forEach((paramName, paramValue) -> {
            if (!PARAM_SIGN.equals(paramName)) {
                checkParams.put(paramName, paramValue);
            }
        });
        return sign.equals(sign(checkParams, secret));
    }

    /**
     * md5加密
     *
     * @param data 加密字符串
     * @return 密文
     */
    private static byte[] encryptMd5(String data) {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes(StandardCharsets.UTF_8));
        } catch (GeneralSecurityException ignored) {

        }
        return bytes;
    }


}