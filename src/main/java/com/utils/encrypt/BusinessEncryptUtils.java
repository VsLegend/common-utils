package com.utils.encrypt;

import com.utils.exception.BusinessException;
import org.springframework.util.Base64Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

/**
 * @Author Wang Junwei
 * @Date 2022/12/22 9:37
 * @Description 业务相关的加解密
 */
public class BusinessEncryptUtils {


    /**
     * 转aes密钥
     *
     * @param base64Key
     * @return
     */
    public static SecretKey convertSecretKey(String base64Key) {
        try {
            return KeyGeneratorUtils.base64ToSecretKey(SecurityConstants.AES, base64Key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("AES自定义密钥恢复失败");
        }
    }


    /**
     * 生成rsa密钥对
     *
     * @return
     */
    public static KeyPair generateKeyPair() {
        // 暂时先生成1024位
        try {
            return KeyGeneratorUtils.getKeyPair(SecurityConstants.RSA, SecurityConstants.PAIR_KEY_SIZE_1024);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new BusinessException("RSA密钥对生成失败，找不到加密算法");
        }
    }


    /**
     * aes密钥加密公私钥
     *
     * @param key
     * @param secretKey
     * @return
     */
    public static String encryptKeyBySecretKey(Key key, SecretKey secretKey) {
        String encoded = Base64Utils.encodeToString(key.getEncoded());
        return aesEncryptBySecretKey(encoded, secretKey);
    }


    /**
     * AES加密（自带密钥）
     *
     * @param content
     * @param base64Key
     * @return
     */
    public static String aesEncryptBySecretKey(String content, String base64Key) {
        SecretKey secretKey = convertSecretKey(base64Key);
        return aesEncryptBySecretKey(content, secretKey);
    }

    /**
     * AES加密（自带密钥）
     *
     * @param content
     * @param secretKey
     * @return
     */
    public static String aesEncryptBySecretKey(String content, SecretKey secretKey) {
        try {
            return Base64Utils.encodeToString(AesUtils.encrypt(content, secretKey));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
            throw new BusinessException("AES加密数据失败");
        }
    }

    /**
     * AES解密（自带密钥）
     *
     * @param content
     * @param base64Key
     * @return
     */
    public static String aesDecryptBySecretKey(String content, String base64Key) {
        SecretKey secretKey = convertSecretKey(base64Key);
        return aesDecryptBySecretKey(content, secretKey);
    }

    /**
     * AES解密
     *
     * @param content
     * @param secretKey
     * @return
     */
    public static String aesDecryptBySecretKey(String content, SecretKey secretKey) {
        try {
            return AesUtils.decrypt(content, secretKey);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
            throw new BusinessException("AES解密数据失败");
        }
    }


}
