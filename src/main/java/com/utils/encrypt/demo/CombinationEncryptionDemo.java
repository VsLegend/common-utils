package com.utils.encrypt.demo;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSONObject;
import com.utils.encrypt.AesUtils;
import com.utils.encrypt.KeyGeneratorUtils;
import com.utils.encrypt.RsaUtils;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.*;
import java.util.Arrays;

import static com.utils.encrypt.SecurityConstants.*;


/**
 * @Author Wang Junwei
 * @Date 2022/11/25 16:32
 * @Description 组合加密
 * <p>
 * base64编码解码过程，需保证使用相同的编码
 * Base64Utils默认使用utf-8
 */
public class CombinationEncryptionDemo {


    private static final String PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDF6JMbdrKr9/0Cw7DIJUVzSgsnBaHzxGJ/lWGd1R+gYhzIvUDznfMh69Aeh82Mvy0cJ60TOJo9VZDH2/GYvjS31Xs53lpoHKTDL13yTTC5tPHkqwlpC8ib83Xrxuo8q41/wbKez2c+WBxXmGIpoWBA7KKA/A5i5OONfFvWE/PVNYYmQqOYpWgtabHlOYc1UbGtXNMVsrJPE4WacpGh7HYW7ueWIXbtCQe7DYlkFaWMTcvh1GrKHhEbMiuLsFyGgPkHeJxbeEKISsIlq7wAk1lkdr9OhlS3KDTrEKI/6ARByQu51QcK7rT1WbzqQZrIrdsJSPYSwuY58nDi3UO4mHOHAgMBAAECggEBAK5P0bn/yxEyhFN5/uvaABiyX65IBMQK9eJ/tRHqFEgUAlxAbMPmG66F37H0VPQhsl65B3UFhrx8799Idrvc09wTGMz4blKC4A/WWX2/xSncGK4ag1hVe6T4Q6WedtNnGmMrPOMptDqyKoxweA1IyNmGUv8egqFSDqagoNamjj37rr2iKtZyxkCSRhPzcUJZ0JxshkdO39M5H/rZU4p034EEIegvy0Cer/5F8V40VvmPaOrfKkJ2qp3ejRXngX32+Bgf2jmRoJDFK0ZGbHiX1h7AJF7kXc+rnYq8G09vMc7Oo5UXkD7f7BifyKOrJccPVmTHlDAPeMedNbMSkYBaIgkCgYEA+zJ6CNN7hC6mN+De7QXpRMXc7FzaVFD5GOOEhyYOC8kQXVmRp8bVSkiDAT3q01dgTlT5ik7YMRHuTHc6R2S0pDa2LujVTGZAtTmocvLTzGwWdGTPzkbz7czwnXANukzYsZa9UwbZ7VRgmxVCv7+sMSWDlTAA2VwaoPuM/nd6kGMCgYEAybFErgPl515f9jjN1dUCbzonU8iCQvTg8k4SiN0kyQAPLb5n41XcUKqjsEfP8Xpq56oiYsT4IBDjWm/MA0m8zL2+F8+e8nxT3/ZukqyYJtEd2OlqwUN9KUXvWWSycarj/FRnh/4RDNRn9gMw8BLjGaetX0FbhfMpd6xGJJdUb40CgYB8sDKZPPi7xTgm1o1xlBSVWa3u+aw6XDM5on3/Y+lm+jgQTkyZvn3TMKkuraBppZ6dY1q2x6pSuTbTZ/9avudX9/x4zoRKNxt5mZQ+8sAlYaXHwt7P4rJkYO0zCRMXXdvWUx/JHcaBIvgNIwGOEsOTZa6qGDjjq+9f5122VnJVRwKBgQCclPnL2wzebb80SW9Lhf5I2a30dqLiVnhYxNHQ3VaUkW43Ri4jKOJM4d0ImwfN4gsi5UuiwGYdht7qtAZ/uvxPOzNcCvzMJkd7hTbcug+5evmgD76oYbvGkhu6m9mJM3Gh0Ok7g7w66J/5NwsEJrWWkj2fyP7D5D6aQO2HtNCBdQKBgQCBGCW8aHQKqkfJsR/EBtrhUVLP4L0zRkep2Puz9Oh2JvewMMe1V+qRLF43B9HZwl9IC0D3kYL4hI0RWEx2cdjl6a2rlDU+x/akfmVFOx2yJGYvuMSvQ7iB4lXCB4gHiUF2I5oEX067aXzJAz2ZeHYoNcYsSkz9/RbJ0IFVXV3HlQ==";

    private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxeiTG3ayq/f9AsOwyCVFc0oLJwWh88Rif5VhndUfoGIcyL1A853zIevQHofNjL8tHCetEziaPVWQx9vxmL40t9V7Od5aaBykwy9d8k0wubTx5KsJaQvIm/N168bqPKuNf8Gyns9nPlgcV5hiKaFgQOyigPwOYuTjjXxb1hPz1TWGJkKjmKVoLWmx5TmHNVGxrVzTFbKyTxOFmnKRoex2Fu7nliF27QkHuw2JZBWljE3L4dRqyh4RGzIri7BchoD5B3icW3hCiErCJau8AJNZZHa/ToZUtyg06xCiP+gEQckLudUHCu609Vm86kGayK3bCUj2EsLmOfJw4t1DuJhzhwIDAQAB";

    private static final String SECRET_KEY = "ODMwNWE2YzVjNzAzYWVjMmE0ZDEzNDA3ZjdlYTQ2NTA=";


    public static void generateKey() throws Exception {
        // 前置证书申请
        KeyPair keyPair = KeyGeneratorUtils.getKeyPair(RSA, PAIR_KEY_SIZE_2048);
        System.out.println("CSC私钥：" + KeyGeneratorUtils.privateKey2Base64(keyPair.getPrivate()));
        System.out.println("CSC公钥：" + KeyGeneratorUtils.publicKey2Base64(keyPair.getPublic()));

        // 前置获取AES密钥
        String dh = "49639940621746";
        String requestId = IdUtil.simpleUUID();
        long timestamp = System.currentTimeMillis();
        SecretKey aesKey = AesDemo.getAesKey(dh, requestId, timestamp);
        System.out.println("AES密钥：" + KeyGeneratorUtils.secretKey2Base64(aesKey));
    }


    /**
     * 请求方组装和加密数据
     *
     * @param secretKey
     * @param publicKey
     * @return
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public static JSONObject request(SecretKey secretKey, PublicKey publicKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        System.out.println("=====================================↓请求方步骤============================================");
        // 1. 需要加密的数据
        String secretData = "夫君子之行，静以修身，俭以养德，非淡泊无以明志，非宁静无以致远。夫学须静也，才须学也，非学无以广才，非志无以成学。淫（慆）慢则不能励精，险躁则不能冶性。年与时驰，意与日去，遂成枯落，多不接世，悲守穷庐，将复何及！";
        System.out.println("加密原文：" + secretData);

        // 2. 准备传输必要的数据
        // 来源
        String origin = "USERID:TYPE:CERTIFICATE-ID";
        String where = null;
        long timestamp = System.currentTimeMillis();
        // 是否共享内容
        boolean share = true;

        System.out.println("来源：" + origin + " 去向：" + where + " 是否共享：" + share);

        // 3. 加密数据
        String encrypt = Base64.encodeBase64String(AesUtils.encrypt(secretData, secretKey));

        // 4. 数据组装准备发送给服务器
        JSONObject json = new JSONObject();
        json.put("origin", origin);
        json.put("where", where);
        json.put("timestamp", timestamp);
        json.put("share-flag", share);
        json.put("encrypt", encrypt);


        // 5. CSC公钥签名
        MD5 md5 = MD5.create();
        byte[] digest = md5.digest(origin + timestamp + share + encrypt);
        String digestEncode = Base64.encodeBase64String(digest);
        byte[] sign = RsaUtils.encrypt(digest, publicKey);
        String signEncode = Base64.encodeBase64String(sign);
        json.put("digestEncode", digestEncode);
        json.put("signEncode", signEncode);
        return json;
    }

    /**
     * 接收方接收数据
     *
     * @throws BadPaddingException 解密失败，密钥不对
     */
    public static void response(JSONObject json, SecretKey secretKey, PrivateKey privateKey) throws Exception {
        System.out.println("=====================================↓接收方步骤============================================");
        System.out.println("数据传输内容：" + json.toJSONString());
        String origin = json.getString("origin");
        String where = json.getString("where");
        boolean share = json.getBoolean("share-flag");
        String encrypt = json.getString("encrypt");
        long timestamp = json.getLong("timestamp");
        String digestEncode = json.getString("digestEncode");
        String signEncode = json.getString("signEncode");

        // 6. CSC校验
        // 时间校验1min内

        // 摘要验证
        MD5 md5 = MD5.create();
        byte[] digestAgain = md5.digest(origin + timestamp + share + encrypt);
        byte[] digestDecode = Base64.decodeBase64(digestEncode);
        System.out.println("摘要信息：" + HexUtil.encodeHexStr(digestAgain));
        System.out.println("完整性校验：" + Arrays.equals(digestAgain, digestDecode));

        // 合法性校验 注：前端加密的MD5的base64编码
        byte[] signDecrypt = RsaUtils.decrypt(signEncode, privateKey);
        String sign = new String(signDecrypt);
        System.out.println(digestEncode);
        System.out.println(sign);
        System.out.println("sign对比校验结果：" + sign.equals(digestEncode));

        // 如果不是share，还要进行来源去向校验

        // 7. 解密内容
        String aesDecrypt = AesUtils.decrypt(encrypt, secretKey);
        System.out.println("解密结果：" + aesDecrypt);
        // 8. 返回解密结果
    }


    public static void main(String[] args) throws Exception {
        System.out.println("摘要信息：" + MD5.create().setSalt("Better_not_decrypt_it".getBytes()).digestHex("123456"));

        SecretKey secretKey = KeyGeneratorUtils.base64ToSecretKey(AES, SECRET_KEY);
        PublicKey publicKey = KeyGeneratorUtils.base64ToPublicKey(RSA, PUBLIC_KEY);
        PrivateKey privateKey = KeyGeneratorUtils.base64ToPrivateKey(RSA, PRIVATE_KEY);

//        JSONObject request = request(secretKey, publicKey);
//
//        String jsonStr = request.toJSONString();
        String jsonStr = "{\n" +
                "\t\"origin\": \"USERID:TYPE:CERTIFICATE-ID\",\n" +
                "\t\"where\": \"兴隆镇\",\n" +
                "\t\"timestamp\": 1669975109000,\n" +
                "\t\"share-flag\": true,\n" +
                "\t\"encrypt\": \"vzwUC0m4ShG005RjWcl5/AifqIxySiQmvf4yw0s9I5aqFkMl3hm+dZGxzDbsUgFy\",\n" +
                "\t\"digestEncode\": \"lWf7/Af02E7gxpzelFTq0A==\",\n" +
                "\t\"signEncode\": \"s4HZOTgyl+H1l7HdIQe6JCMjhyyWxI67aqFCFzmv5ForJssZ5efNJ9oFZbyigCTkXl/npptI8y4fT6cWaaiSaMwwknCKNG+sJ2DNZKcNeCDx0OdH9PBmYrwXOT5kDj6tLbjRhjKbD2fzPRIEZHRCWGehEnb96dGR8+hW9hQRTDyl0mVAY73pI2i9h2CTNLWvZMJqqxfhb4QYc2tYlp5tO7SzdNs4WOXporPHru4fgtwFl8o9YTc0KAkGB1N35n4a5h7xH66UD6ihteldR6iAI5sd6jWUvGyOec5Suu1GqXBcY6KmKc2OddruivXr0zXYVF1GW5NJ0TwTWq5NZcc/YA==\"\n" +
                "}";

        JSONObject json = JSONObject.parseObject(jsonStr);
        response(json, secretKey, privateKey);
    }

}
