package com.utils.encrypt;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static com.utils.encrypt.SecurityConstants.*;
import static com.utils.encrypt.SecurityConstants.RNG_SHA1RNG;

/**
 * @Author Wang Junwei
 * @Date 2022/11/24 14:27
 * @Description key生成器
 */
public class KeyGeneratorUtils {


    /**
     * =============================================================================================================
     * ===================================================非对称加密==================================================
     * =============================================================================================================
     */

    /**
     * 非对称加密——生成秘钥对
     *
     * @param algorithm 算法
     * @param keySize   bit
     * @return
     * @throws Exception
     */
    public static KeyPair getKeyPair(String algorithm, int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 非对称加密——公钥转Base64编码
     *
     * @param publicKey
     * @return
     */
    public static String publicKey2Base64(PublicKey publicKey) {
        byte[] bytes = publicKey.getEncoded();
        return Base64.encodeBase64String(bytes);
    }


    /**
     * 非对称加密——私钥转Base64编码
     *
     * @param privateKey
     * @return
     */
    public static String privateKey2Base64(PrivateKey privateKey) {
        byte[] bytes = privateKey.getEncoded();
        return Base64.encodeBase64String(bytes);
    }


    /**
     * base64编码转publicKey
     *
     * @param base64
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey base64ToPublicKey(String algorithm, String base64) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decode = Base64.decodeBase64(base64);
        KeySpec keySpec = new X509EncodedKeySpec(decode);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(keySpec);
    }


    /**
     * base64编码转privateKey
     *
     * @param base64
     * @return
     * @throws Exception
     */
    public static PrivateKey base64ToPrivateKey(String algorithm, String base64) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(base64);
        KeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePrivate(keySpec);
    }


    /**
     * =============================================================================================================
     * ====================================================对称加密==================================================
     * =============================================================================================================
     */


    /**
     * 对称加密——生成密钥
     *
     * @param seed
     * @param keySize
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static SecretKey getAesSecretKey(String algorithm, String seed, int keySize) throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance(algorithm);
        SecureRandom random = SecureRandom.getInstance(RNG_SHA1RNG);
        // 设置随机密码的种子值
        random.setSeed(seed.getBytes(StandardCharsets.UTF_8));
        // 密钥长度为 128/192/256 位
        kg.init(keySize, random);
        SecretKey secretKey = kg.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), algorithm);
    }


    /**
     * 对称加密——SecretKey转Base64编码
     *
     * @param secretKey
     * @return
     */
    public static String secretKey2Base64(SecretKey secretKey) {
        byte[] bytes = secretKey.getEncoded();
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 对称加密——Base64编码转SecretKey
     *
     * @param algorithm
     * @param base64
     * @return
     * @throws Exception
     */
    public static SecretKey base64ToSecretKey(String algorithm, String base64) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(base64);
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, algorithm);
    }

}
