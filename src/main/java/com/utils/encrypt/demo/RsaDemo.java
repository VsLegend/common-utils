package com.utils.encrypt.demo;

import cn.hutool.core.util.HexUtil;
import com.utils.encrypt.KeyGeneratorUtils;
import com.utils.encrypt.RsaUtils;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import static com.utils.encrypt.SecurityConstants.RSA;

/**
 * @Author Wang Junwei
 * @Date 2022/11/30 16:44
 * @Description rsa
 */
public class RsaDemo {


    /**
     * 模拟数据
     *
     * @param bitLength
     * @return
     */
    public static byte[] getData(int bitLength) {
        byte[] bytes = new byte[bitLength];
        new Random().nextBytes(bytes);
        return bytes;
    }


    /**
     * 长度测试
     * RSA4096位支持的加密byte长度：4096 / 8 - 10
     *
     * @param data
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public static void testLength(byte[] data, int keySize) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        // 生成密钥对
        KeyPair keyPair = KeyGeneratorUtils.getKeyPair(RSA, keySize);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        System.out.println("加密原文：" + Base64.encodeBase64String(data));

        byte[] encrypt = RsaUtils.encrypt(data, privateKey);
        System.out.println("加密密文：" + Base64.encodeBase64String(encrypt));

        byte[] decrypt = RsaUtils.decrypt(encrypt, publicKey);
        System.out.println("解密原文：" + Base64.encodeBase64String(decrypt));

    }

    public static void decrypt() throws InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        String publicStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCFKyt29gW2K/m4bP2vve9SR+l4B0mBhXAs92tvz4lMmY/wlltGohpg+r3ur0wtXzffSvFZXt4Fj2PsWpt3N0/jax1AxVXpYY/3FAf25DsW5Eb6w5MByEAxCBy/dtHLPLfvxWUKmG53fm2JN+SpOji0ISZ8f+DFsCXVDYQRUMHpxwIDAQAB";
        String sign = "UQIzChP+iB67y6mOoNBA35ZfkJgJ4oZR1TUNvA0l5LmAAv5WkAWmURuwX0S6LaaQDEbC9NVF77NuLYXJdWSUwzrkvbcDB2ewfjwTmMezdbhpRZAa/lA6hmED2Oub9MuEUM0ULkAS6BWjvKPezwTISYNrqHfU7V5cR+Bd6rjrg3s=";

        PublicKey publicKey = KeyGeneratorUtils.base64ToPublicKey(RSA, publicStr);
        byte[] decrypt = RsaUtils.decrypt(sign, publicKey);
        System.out.println(HexUtil.encodeHexStr(decrypt));
        System.out.println(Base64.encodeBase64String(decrypt));
    }

    public static void main(String[] args) throws BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        decrypt();
    }

}
