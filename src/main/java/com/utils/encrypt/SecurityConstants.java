package com.utils.encrypt;

/**
 * @Author Wang Junwei
 * @Date 2022/11/24 14:35
 * @Description 加密算法常量
 */
public class SecurityConstants {

    /**
     * 非对称加密算法
     */
    public static final String RSA = "RSA";


    /**
     * RAS 模式
     * padding数据最多加密：key_size / 8 - 11
     * no padding数据最多加密：key_size / 8 - 2
     */
    public static final String RSA_ECB = "RSA/ECB/PKCS1Padding";
    public static final String RSA_ECB_NO_PADDING = "RSA/ECB/NoPadding";

    /**
     * 密钥交换算法
     */
    public static final String DH = "DH";


    /**
     * 非对称加密允许的长度
     */
    public static final int PAIR_KEY_SIZE_512 = 512;
    public static final int PAIR_KEY_SIZE_1024 = 1024;
    public static final int PAIR_KEY_SIZE_2048 = 2048;
    public static final int PAIR_KEY_SIZE_4096 = 4096;



    /**
     * 对称加密算法
     */
    public static final String AES = "AES";

    /**
     * AES
     */
    public static final String AES_ECB = "AES/ECB/PKCS5Padding";
    public static final String AES_ECB_NO_PADDING = "AES/ECB/NoPadding";
    public static final String AES_CBC = "AES/CBC/PKCS5Padding";
    public static final String AES_CBC_NO_PADDING = "AES/CBC/NoPadding";


    /**
     * AES加密长度128/192/256bit 越长加解密时间越长
     */
    public static final int SECRET_KEY_SIZE_128 = 128;
    public static final int SECRET_KEY_SIZE_192 = 192;
    public static final int SECRET_KEY_SIZE_256 = 256;


    /**
     * 随机数生成算法 RNG Algorithm
     * @see <a url="https://docs.oracle.com/javase/8/docs/technotes/guides/security/SunProviders.html#SecureRandomImp>Java Cryptography Architecture Oracle Providers Documentation for JDK 8</>
     */
    public static final String RNG_SHA1RNG = "SHA1PRNG";
    public static final String RNG_PKCS11 = "PKCS11";

}
