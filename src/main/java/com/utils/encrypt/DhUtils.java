package com.utils.encrypt;


import cn.hutool.crypto.digest.MD5;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

import static com.utils.encrypt.SecurityConstants.AES;
import static com.utils.encrypt.SecurityConstants.DH;

/**
 * @Author Wang Junwei
 * @Date 2022/11/24 10:46
 * @Description dh密钥交换算法
 * @see <a url="https://www.apiref.com/java11-zh/java.base/javax/crypto/KeyAgreement.html">Class KeyAgreement</>
 * @see <a url="https://zh.wikipedia.org/wiki/%E8%BF%AA%E8%8F%B2-%E8%B5%AB%E7%88%BE%E6%9B%BC%E5%AF%86%E9%91%B0%E4%BA%A4%E6%8F%9B">迪菲-赫尔曼密钥交换</>
 * @see com.utils.encrypt.demo.DhDemo
 * @see com.utils.encrypt.demo.SimpleDhDemo
 */
public class DhUtils {

    /**
     * 计算步骤
     *
     * 1.
     * 甲乙双方设定g和p得值，甲随机设置a，将求模的计算结果A发送给乙
     * 甲: A = g<sup>a</sup> mod p
     *
     *
     * 2.
     * 甲将数据传输给乙
     *         A
     * 甲    ----->    乙
     *
     *
     * 3.
     * 设置随机值b，求模，将结果发送给甲，同时自己得到这个密钥
     * 乙: B = g<sup>b</sup> mod p
     * 乙：K = A<sup>b</sup> mod p
     *
     *
     * 4.
     * 甲将数据传输给乙
     *         B
     * 乙    ----->    甲
     *
     * 5.
     * 甲根据乙的返回值B，得到密钥，这个密钥与乙得到K的相同，因为在模p下g^a*b和g^b*a相等
     * 甲：K = B<sup>a</sup> mod p
     *
     *
     */

    /**
     * 计算交换结果
     */
    public static BigInteger calcResult(BigInteger p, int g, int random) {
        BigInteger pow = BigInteger.valueOf(g).pow(random);
        return pow.mod(p);
    }


    /**
     * 根据交换结果计算密钥种子
     *
     * @param p
     * @param random
     * @param result
     * @return
     */
    public static BigInteger getSeedKey(BigInteger p, int random, BigInteger result) {
        // 密钥
        BigInteger pow = result.pow(random);
        return pow.mod(p);
    }

    /**
     * DH最终密钥生成
     *
     * @param dh
     * @param requestId
     * @param timestamp
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static SecretKey generateAesKey(BigInteger dh, Integer requestId, long timestamp) throws Exception {
        // 种子
        String seed = dh.toString() + requestId + timestamp;
        // md5哈希seed 32byte
        String md5 = MD5.create().digestHex(seed);
        // 生成
        byte[] key = md5.getBytes(StandardCharsets.UTF_8);
        String encode = Base64.encodeBase64String(key);
        return  KeyGeneratorUtils.base64ToSecretKey(AES, encode);
    }



    /**
     * dh1：
     * 计算通过KeyPairGenerator生成的DH各项参数的结果 （仅适用于两者的密钥交换，多方密钥交换，第一次调用需要将doPhase参数设为false，后续设为true）
     * <p>
     * 适用于服务器交换，与前端交互时，只有提取出g p a参数在与之计算才能交互得出结果
     *
     * @param privateKeyA 乙的私钥
     * @param publicKeyB  甲的公钥（包含了g p a参数）
     * @return 最终的密钥Key
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] generateSecreteKey(PrivateKey privateKeyA, PublicKey publicKeyB) throws NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        Objects.requireNonNull(privateKeyA);
        Objects.requireNonNull(publicKeyB);
        if (!DH.equals(privateKeyA.getAlgorithm()) || !DH.equals(publicKeyB.getAlgorithm())) {
            throw new InvalidAlgorithmParameterException("DH Algorithm is allowed");
        }
        KeyAgreement keyAgreement = KeyAgreement.getInstance(DH);
        keyAgreement.init(privateKeyA);
        keyAgreement.doPhase(publicKeyB, true);
        return keyAgreement.generateSecret();
    }





    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
//        DHPublicKey dhPublicKey = (DHPublicKey) aPublicKey;
//        DHParameterSpec params = dhPublicKey.getParams();
    }

}
