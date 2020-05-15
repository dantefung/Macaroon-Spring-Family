package com.dantefung.springbootmvc.utils;

import com.dantefung.springbootmvc.exception.SignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RSAEncrypt {

    // MAX_DECRYPT_BLOCK应等于密钥长度/8（1byte=8bit），所以当密钥位数为2048时，最大解密长度应为256.
    // 128 对应 1024，256对应2048
    private static final int KEYSIZE = 2048;

    // RSA最大加密明文大小
    private static final int MAX_ENCRYPT_BLOCK = 117;

    // RSA最大解密密文大小
    private static final int MAX_DECRYPT_BLOCK = 128;

    // 不仅可以使用DSA算法，同样也可以使用RSA算法做数字签名
    private static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    public static final String DEFAULT_SEED = "$%^*%^()(ED47d784sde78"; // 默认种子

    public static final String PUBLIC_KEY = "PublicKey";
    public static final String PRIVATE_KEY = "PrivateKey";

    private RSAEncrypt(){}
    /**
     *
     * 生成密钥
     *
     * @param seed 种子
     *
     * @return 密钥对象
     * @throws Exception
     *
     */

    public static Map<String, Key> initKey(String seed)  {
        log.info("生成密钥");
        try{
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
        }catch (NoSuchAlgorithmException e){
            log.error("",e);
            throw  new SignException(e.getMessage());
        }

    }

    /**
     *
     * 生成默认密钥
     *
     *
     * @return 密钥对象
     * @throws Exception
     *
     */

    public static Map<String, Key> initKey()  {
        return initKey(DEFAULT_SEED);
    }

    /**
     *
     * 取得私钥
     *
     * @param keyMap
     *
     * @return
     * @throws Exception
     *
     */
    public static String getPrivateKey(Map<String, Key> keyMap)  {
        Key key = keyMap.get(PRIVATE_KEY);
        return encryptBASE64(key.getEncoded()); // base64加密私钥
    }

    /**
     *
     * 取得公钥
     *
     * @param keyMap
     *
     * @return
     * @throws Exception
     *
     */
    public static String getPublicKey(Map<String, Key> keyMap)  {
        Key key = keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded()); // base64加密公钥
    }

    /**
     *
     * 用私钥对信息进行数字签名
     *
     * @param data       加密数据
     *
     * @param privateKey 私钥-base64加密的
     *
     * @return
     *
     * @throws Exception
     *
     */
    public static String signByPrivateKey(byte[] data, String privateKey)  {
        log.info("用私钥对信息进行数字签名");
        byte[] keyBytes = decryptBASE64(privateKey);
        try{
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey priKey = factory.generatePrivate(keySpec);// 生成私钥
            // 用私钥对信息进行数字签名
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(priKey);
            signature.update(data);
            return encryptBASE64(signature.sign());
        }catch (Exception e){
            log.error("",e);
            throw new SignException(e.getMessage());
        }


    }

    /**
     *
     * BASE64Encoder 加密
     *
     * @param data 要加密的数据
     *
     * @return 加密后的字符串
     *
     */
    private static String encryptBASE64(byte[] data) {

        return new String(Base64.encodeBase64(data));
    }

    private static byte[] decryptBASE64(String data) {

        return Base64.decodeBase64(data);
    }

    public static boolean verifyByPublicKey(byte[] data, String publicKey, String sign)  {
        byte[] keyBytes = decryptBASE64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        try{
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey pubKey = keyFactory.generatePublic(keySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(pubKey);
            signature.update(data);
            return signature.verify(decryptBASE64(sign)); // 验证签名
        }catch (Exception e){
            log.error("",e);
            throw new SignException(e.getMessage());
        }

    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws Exception                    加密过程中的异常信息
     */
    public static String encryptByPublicKey(String str, String publicKey) {
        // base64编码的公钥
        byte[] keyBytes = decryptBASE64(publicKey);
        try{
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM)
                    .generatePublic(new X509EncodedKeySpec(keyBytes));
            // RSA加密
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);

            byte[] data = str.getBytes(StandardCharsets.UTF_8);
            // 加密时超过117字节就报错。为此采用分段加密的办法来加密
            byte[] enBytes = null;
            for (int i = 0; i < data.length; i += MAX_ENCRYPT_BLOCK) {
                // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
                byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + MAX_ENCRYPT_BLOCK));
                enBytes = ArrayUtils.addAll(enBytes, doFinal);
            }
            return encryptBASE64(enBytes);
        }catch (Exception e){
            log.error("",e);
            throw new SignException(e.getMessage());
        }

    }

    /**
     * RSA私钥加密
     *
     * @param str        加密字符串
     * @param privateKey 公钥
     * @return 密文
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws Exception                    加密过程中的异常信息
     */
    public static String encryptByPrivateKey(String str, String privateKey)
            {
        // base64编码的公钥
        byte[] keyBytes = decryptBASE64(privateKey);
        try{
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(KEY_ALGORITHM)
                    .generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
            // RSA加密
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, priKey);

            byte[] data = str.getBytes(StandardCharsets.UTF_8);
            // 加密时超过117字节就报错。为此采用分段加密的办法来加密
            byte[] enBytes = null;
            for (int i = 0; i < data.length; i += MAX_ENCRYPT_BLOCK) {
                // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
                byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + MAX_ENCRYPT_BLOCK));
                enBytes = ArrayUtils.addAll(enBytes, doFinal);
            }
            return encryptBASE64(enBytes);
        }catch (Exception e){
            log.error("",e);
            throw new SignException(e.getMessage());
        }

    }

    /**
     * 读取公钥
     *
     * @param publicKeyPath
     * @return
     */
    public static PublicKey readPublic(String publicKeyPath) {
        if (publicKeyPath != null) {
            try (FileInputStream bais = new FileInputStream(publicKeyPath)) {
                CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
                X509Certificate cert = (X509Certificate) certificatefactory.generateCertificate(bais);
                return cert.getPublicKey();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 读取私钥
     *
     * @param path
     * @return
     */
    public static PrivateKey readPrivate(String privateKeyPath, String privateKeyPwd) {
        if (privateKeyPath == null || privateKeyPwd == null) {
            return null;
        }
        try (InputStream stream = new FileInputStream(new File(privateKeyPath));) {
            // 获取JKS 服务器私有证书的私钥，取得标准的JKS的 KeyStore实例
            KeyStore store = KeyStore.getInstance("JKS");// JKS，二进制格式，同时包含证书和私钥，一般有密码保护；PKCS12，二进制格式，同时包含证书和私钥，一般有密码保护。
            // jks文件密码，根据实际情况修改
            store.load(stream, privateKeyPwd.toCharArray());
            // 获取jks证书别名
            Enumeration<String> en = store.aliases();
            String pName = null;
            while (en.hasMoreElements()) {
                String n =  en.nextElement();
                if (store.isKeyEntry(n)) {
                    pName = n;
                }
            }
            // 获取证书的私钥
           return (PrivateKey) store.getKey(pName, privateKeyPwd.toCharArray());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * RSA私钥解密
     *
     * @param encryStr   加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws Exception                 解密过程中的异常信息
     */
    public static String decryptByPrivateKey(String encryStr, String privateKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {
        // base64编码的私钥
        byte[] decoded = decryptBASE64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(KEY_ALGORITHM)
                .generatePrivate(new PKCS8EncodedKeySpec(decoded));
        // RSA解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, priKey);

        // 64位解码加密后的字符串
        byte[] data = decryptBASE64(encryStr);
        // 解密时超过128字节报错。为此采用分段解密的办法来解密
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i += MAX_DECRYPT_BLOCK) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + MAX_DECRYPT_BLOCK));
            sb.append(new String(doFinal));
        }

        return sb.toString();
    }

    /**
     * RSA公钥解密
     *
     * @param encryStr   加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws Exception                 解密过程中的异常信息
     */
    public static String decryptByPublicKey(String encryStr, String publicKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {
        // base64编码的私钥
        byte[] decoded = decryptBASE64(publicKey);
        RSAPublicKey priKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM)
                .generatePublic(new X509EncodedKeySpec(decoded));
        // RSA解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, priKey);

        // 64位解码加密后的字符串
        byte[] data = decryptBASE64(encryStr);
        // 解密时超过128字节报错。为此采用分段解密的办法来解密
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i += MAX_DECRYPT_BLOCK) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + MAX_DECRYPT_BLOCK));
            sb.append(new String(doFinal));
        }

        return sb.toString();
    }

}
