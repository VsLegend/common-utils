package com.utils.encrypt;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;

import static com.utils.encrypt.SecurityConstants.AES;
import static com.utils.encrypt.SecurityConstants.AES_ECB;

/**
 * @Author Wang Junwei
 * @Date 2022/11/24 14:44
 * @Description AES with Cipher
 *
 * @see <a url="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">Cipher</a>
 */
public class AesUtils {

    /**
     * AES 加密操作
     *
     * @param originContent 待加密内容
     * @param secretKey 密钥key
     * @return 返回Base64转码后的加密数据
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static byte[] encrypt(String originContent, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        byte[] byteContent = originContent.getBytes(StandardCharsets.UTF_8);
        return encrypt(byteContent, secretKey);
    }

    public static byte[] encrypt(byte[] originContent, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(AES_ECB);
        // 加密模式
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(originContent);
    }


    /**
     * AES 解密操作
     *
     * @param secretContent 带解密内存
     * @param secretKey 密钥key
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static String decrypt(String secretContent, String secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        byte[] decode = Base64.decodeBase64(secretContent);
        byte[] secret = Base64.decodeBase64(secretKey);
        return decrypt(decode, new SecretKeySpec(secret, AES));
    }

    public static String decrypt(byte[] secretContent, byte[] secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        SecretKeySpec key = new SecretKeySpec(secretKey, AES);
        return decrypt(secretContent, key);
    }

    public static String decrypt(String secretContent, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        byte[] decode = Base64.decodeBase64(secretContent);
        return decrypt(decode, secretKey);
    }

    public static String decrypt(byte[] secretContent, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(AES_ECB);
        // 解密模式
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] result = cipher.doFinal(secretContent);
        return new String(result, StandardCharsets.UTF_8);
    }
}
