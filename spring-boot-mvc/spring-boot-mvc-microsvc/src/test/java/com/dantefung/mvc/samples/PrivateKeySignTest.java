package com.dantefung.mvc.samples;

import com.dantefung.springbootmvc.exception.SignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Admin
 * @email johnhohwang@163.com
 * @date 17:22
 */
@Slf4j
public class PrivateKeySignTest {
    // 不仅可以使用DSA算法，同样也可以使用RSA算法做数字签名
    private static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    public static final String DEFAULT_SEED = "$%^*%^()(ED47d784sde78"; // 默认种子

     public static final String APP_SYS_ID = "fhltest2d887f6e6d54478c82c7c3701";
    public static final Long TIME_STAMP = 1577525518L;
    public static final String PUBLIC_KEY = "PublicKey";
    public static final String PRIVATE_KEY = "PrivateKey";
    // MAX_DECRYPT_BLOCK应等于密钥长度/8（1byte=8bit），所以当密钥位数为2048时，最大解密长度应为256.
    // 128 对应 1024，256对应2048
    private static final int KEYSIZE = 2048;

    // RSA最大加密明文大小
    private static final int MAX_ENCRYPT_BLOCK = 117;

    // RSA最大解密密文大小
    private static final int MAX_DECRYPT_BLOCK = 128;

	/** sonar **/
	@Test
	public void testOk() {
		log.info("ok!");
		Assert.assertTrue(true);
	}

    public static void main(String[] args) {

        //生成秘钥
        Map<String, Key> map = PrivateKeySignTest.initKey(APP_SYS_ID);
        String privateKey = getPrivateKey(map);
        log.info("privateKey is :>>>>>>>{}", privateKey);
        //公钥
        String publicKey = getPublicKey(map);
        log.info("publicKey is :>>>>>>>{}", publicKey);
        //明文
        //String plaintext = APP_SYS_ID + String.valueOf(TIME_STAMP);
        String plaintext = APP_SYS_ID ;
        byte[] data = plaintext.getBytes();
        //获取签名
        String sign = signByPrivateKey(data, privateKey);
        boolean result = verifyByPublicKey(data, publicKey, sign);
        if (result) {
            log.info("result is :>>>>>>>ok");
        } else {
            log.info("result is :>>>>>>>fail");
        }
        //测试获取sign
        String signOfBS = signByPrivateKey(data, privateKey);
        log.info("signOfBS is :>>>>>>>>{}", signOfBS);
    }


    private static byte[] decryptBASE64(String data) {

        return Base64.decodeBase64(data);
    }

    private static String encryptBASE64(byte[] data) {

        return new String(Base64.encodeBase64(data));
    }

    public static boolean verifyByPublicKey(byte[] data, String publicKey, String sign) {
        byte[] keyBytes = decryptBASE64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey pubKey = keyFactory.generatePublic(keySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(pubKey);
            signature.update(data);
            return signature.verify(decryptBASE64(sign)); // 验证签名
        } catch (Exception e) {
            log.error("", e);
            throw new SignException(e.getMessage());
        }
    }

    public static String signByPrivateKey(byte[] data, String privateKey) {
        log.info("用私钥对信息进行数字签名");
        byte[] keyBytes = decryptBASE64(privateKey);
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey priKey = factory.generatePrivate(keySpec);// 生成私钥
            // 用私钥对信息进行数字签名
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(priKey);
            signature.update(data);
            return encryptBASE64(signature.sign());
        } catch (Exception e) {
            log.error("", e);
            throw new SignException(e.getMessage());
        }
    }

    public static String getPrivateKey(Map<String, Key> keyMap) {
        Key key = keyMap.get(PRIVATE_KEY);
        return encryptBASE64(key.getEncoded()); // base64加密私钥
    }

    public static String getPublicKey(Map<String, Key> keyMap) {
        Key key = keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded()); // base64加密公钥
    }

    public static Map<String, Key> initKey(String seed) {
        log.info("生成密钥");
        try {
            KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom secureRandom = new SecureRandom();
            // 如果指定seed，那么secureRandom结果是一样的，所以生成的公私钥也永远不会变
            secureRandom.setSeed(seed.getBytes());
            // Modulus size must range from 512 to 1024 and be a multiple of 64
            keygen.initialize(KEYSIZE, secureRandom);
            KeyPair keys = keygen.genKeyPair();
            PrivateKey privateKey = keys.getPrivate();
            PublicKey publicKey = keys.getPublic();
            Map<String, Key> map = new HashMap<>(2);
            map.put(PUBLIC_KEY, publicKey);
            map.put(PRIVATE_KEY, privateKey);
            return map;
        } catch (NoSuchAlgorithmException e) {
            log.error("", e);
            throw new SignException(e.getMessage());
        }

    }
}
