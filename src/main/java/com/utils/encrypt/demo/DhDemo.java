package com.utils.encrypt.demo;

import com.utils.encrypt.DhUtils;
import com.utils.encrypt.KeyGeneratorUtils;
import org.apache.commons.codec.binary.Base64;

import java.security.*;
import java.security.spec.InvalidKeySpecException;

import static com.utils.encrypt.SecurityConstants.DH;
import static com.utils.encrypt.SecurityConstants.PAIR_KEY_SIZE_512;

/**
 * @Author Wang Junwei
 * @Date 2022/11/24 16:12
 * @Description
 */
public class DhDemo {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        // 1. a b分别创建公私钥对
        KeyPair a = KeyGeneratorUtils.getKeyPair(DH, PAIR_KEY_SIZE_512);
        KeyPair b = KeyGeneratorUtils.getKeyPair(DH, PAIR_KEY_SIZE_512);

        // 2. 转base64，双方进行网络传输
        String aBase64 = KeyGeneratorUtils.publicKey2Base64(a.getPublic());
        String bBase64 = KeyGeneratorUtils.publicKey2Base64(b.getPublic());

        // 3. a计算出key
        PublicKey bPublicKey = KeyGeneratorUtils.base64ToPublicKey(DH, bBase64);
        String aKey = Base64.encodeBase64String(DhUtils.generateSecreteKey(a.getPrivate(), bPublicKey));

        // 4. b计算出key
        PublicKey aPublicKey = KeyGeneratorUtils.base64ToPublicKey(DH, aBase64);
        String bKey = Base64.encodeBase64String(DhUtils.generateSecreteKey(b.getPrivate(), aPublicKey));

        System.out.println(aKey);
        System.out.println(bKey);

        // 对称加密使用key加密数据，a将数据发送给b，b使用key解密
    }
}
