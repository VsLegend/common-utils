package com.utils.encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

import static com.utils.encrypt.SecurityConstants.*;
import static com.utils.encrypt.SecurityConstants.PAIR_KEY_SIZE_1024;
import static com.utils.encrypt.SecurityConstants.RSA;
import static com.utils.encrypt.SecurityConstants.RSA_ECB;

/**
 * @Author Wang Junwei
 * @Date 2022/11/24 15:12
 * @Description Cipher with Cipher
 */
public class RsaUtils {

    /**
     * 私钥|公钥 加密
     *
     * @param originContent 原始数据
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String originContent, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return encrypt(Base64.decodeBase64(originContent), key);
    }

    public static byte[] encrypt(byte[] originContent, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(RSA_ECB);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(originContent);
    }

    public static String encryptEncode(byte[] originContent, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(RSA_ECB);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.encodeBase64String(cipher.doFinal(originContent));
    }

    /**
     * 私钥|公钥 解密
     *
     * @param contentBase64   加密数据
     * @param key 私钥|公钥
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(String contentBase64, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        byte[] decode = Base64.decodeBase64(contentBase64);
        return decrypt(decode, key);
    }

    public static byte[] decrypt(byte[] encryptionContent, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(RSA_ECB);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encryptionContent);
    }


    public static void main(String[] args) throws Exception {
//        // 生成密钥对
        KeyPair keyPair = KeyGeneratorUtils.getKeyPair(RSA, PAIR_KEY_SIZE_1024);
        System.out.println("-------------------公钥-------------------");
        System.out.println(Base64.encodeBase64String(keyPair.getPublic().getEncoded()));
        System.out.println("-------------------私钥-------------------");
        System.out.println(Base64.encodeBase64String(keyPair.getPrivate().getEncoded()));
    }

}
