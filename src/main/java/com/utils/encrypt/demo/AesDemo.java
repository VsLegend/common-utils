package com.utils.encrypt.demo;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.MD5;
import com.utils.encrypt.AesUtils;
import com.utils.encrypt.KeyGeneratorUtils;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.utils.encrypt.SecurityConstants.AES;
import static com.utils.encrypt.SecurityConstants.SECRET_KEY_SIZE_256;


/**
 * @Author Wang Junwei
 * @Date 2022/11/30 15:14
 * @Description aes与dh组合测试
 */
public class AesDemo {

    /**
     * 默认种子，仅供测试用
     */
    public static final String DEFAULT_TEST_SEED = "NSsu928&w012~3NSisd-csfps---two_snail_for_security";

    /**
     * 测试aes 和 dh组合
     *
     * @param origin
     * @param dh
     * @param requestId
     * @param timestamp
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public static void testAesWithDh(String origin, String dh, String requestId, long timestamp) throws Exception {
        // 密钥生成
        SecretKey secretKey = getAesKey(dh, requestId, timestamp);
        String encode = Base64.encodeBase64String(secretKey.getEncoded());
        System.out.println("AES加密密钥：" + encode);

        // 加密
        String encrypt = Base64.encodeBase64String(AesUtils.encrypt(origin, secretKey));


        // 模拟传输
        SecretKey secretKey1 = KeyGeneratorUtils.base64ToSecretKey(AES, encode);
        // 解密
        String decrypt = AesUtils.decrypt(encrypt, secretKey1);

        System.out.println("AES加密密文：" + encrypt);
        System.out.println("AES解密密文：" + decrypt);
    }

    /**
     * 密钥生成
     *
     * @param dh
     * @param requestId
     * @param timestamp
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static SecretKey getAesKey(String dh, String requestId, long timestamp) throws Exception {
        // 种子
        String seed = dh + requestId + timestamp;
        // md5哈希seed 32byte
        String md5 = MD5.create().digestHex(seed);
        // 生成
        byte[] key = md5.getBytes(StandardCharsets.UTF_8);
        String encode = Base64.encodeBase64String(key);
        System.out.println("加密SEED：" + seed);
        System.out.println("加密密钥：" + md5);
        System.out.println("加密密钥base64：" + encode);
        return  KeyGeneratorUtils.base64ToSecretKey(AES, encode);
    }


    /**
     * 测试前端解密
     *
     * @param keyEncode
     * @param encrypted
     * @return
     * @throws Exception
     */
    public static String testDecrypt(String keyEncode, String encrypted) throws Exception {
        SecretKey secretKey = KeyGeneratorUtils.base64ToSecretKey(AES, keyEncode);
        return AesUtils.decrypt(encrypted, secretKey);
    }

    /**
     * 测试加密解密流程
     *
     * @param origin
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws NoSuchPaddingException
     */
    public static void testAesProcess(String origin) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        // 加密数据
        // 密钥生成
        SecretKey secretKey = KeyGeneratorUtils.getAesSecretKey(AES, DEFAULT_TEST_SEED, SECRET_KEY_SIZE_256);
        byte[] encoded = secretKey.getEncoded();
        System.out.println("AES加密密钥(base64)：" + Base64.encodeBase64String(encoded));

        // 加密
        String encrypt = Base64.encodeBase64String(AesUtils.encrypt(origin, secretKey));
        // 解密
        String decrypt = AesUtils.decrypt(encrypt, secretKey);

        System.out.println(encrypt);
        System.out.println(decrypt);
    }


    /**
     * 测试自建32byte加密密钥
     * @throws Exception
     */
    public static void testCreateSecretKey() throws Exception {
        String origin = "{\"schemeId\":288,\"maintains\":[{\"operationAlias\":\"123\",\"sequence\":0,\"operationTables\":[{\"relationName\":\"123\",\"remark\":\"123\",\"relationId\":247,\"relationType\":1}],\"spotTables\":[],\"feedbackTables\":[]}],\"needConfirm\":true,\"serverId\":39,\"insId\":101138}";
        String dh = "49639940621746";
        String requestId = IdUtil.simpleUUID();
        long timestamp = System.currentTimeMillis();
        SecretKey secretKey = getAesKey(dh, requestId, timestamp);
        byte[] aesEncrypt = AesUtils.encrypt(origin, secretKey);
        String encrypt = Base64.encodeBase64String(aesEncrypt);
        String decrypt = AesUtils.decrypt(encrypt, secretKey);
        System.out.println("加密密文：" + encrypt);
        System.out.println("解密明文：" + decrypt);
    }

    public static void testDhKey() throws Exception {
        String content = "2woniu_223344_T@content*999";
        String webKey = "0f535571eb8efbe554ae3e90256d0081";
        String webEncrypt = "sCfEGAXv9rJdxWz24ifVmk5ykhpfQt1JmgYjCeqrKgo=";
//        String aesEncrypt = "291ARYng2oVuneUk31Es16W1zBuERXG6UrjCkFrwjmTKnFDCyB+nS018MJZK+ig5";
//        String aesEncryptKey = "9wUwRBqonT4/305B7ejKc9UUqSUyG9pmWZ7WQVsBMPo=";
        byte[] webKeyBytes = webKey.getBytes(StandardCharsets.UTF_8);
        String encode = Base64.encodeBase64String(webKeyBytes);
        String serverEncode = "NzJmMzdjZThlNzJmZGZmYjRjMzdmYTVlMzk3ZTFiYzY=";
        System.out.println("前端密钥base64：" + encode);
        System.out.println("后端密钥base64：" + serverEncode);
        SecretKey secretKey = KeyGeneratorUtils.base64ToSecretKey(AES, encode);
        SecretKey serverSecretKey = KeyGeneratorUtils.base64ToSecretKey(AES, serverEncode);

        String decrypt = AesUtils.decrypt(webEncrypt, secretKey);
        String serverDecrypt = AesUtils.decrypt(webEncrypt, serverSecretKey);
        System.out.println("WEB端密钥解密：" + decrypt);
        System.out.println("服务端密钥解密：" + serverDecrypt);
    }

    public static void main(String[] args) throws Exception {
        testDhKey();
//        testCreateSecretKey();
//        testAesProcess("123");
    }

}
