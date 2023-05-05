package com.utils.encrypt.demo;


import java.math.BigInteger;
import java.util.Random;

/**
 * @Author Wang Junwei
 * @Date 2022/11/29 9:24
 * @Description DH算法
 * 注意事项：
 * 1. g通常为2或5，2-5之间也可以
 * 2. p应设置为正质素
 * 3. 当p为200-300，a、b为100长，那么全世界的计算机加起来也几乎无法计算出a、b
 * 3. 甲乙的私钥(即a, b)和p越大，整个计算消耗时间越长
 * <p>
 * long：BigInteger.valueOf(2).pow(64) - 1
 */
public class SimpleDhDemo {

    /**
     * 计算交换结果
     */
    public static BigInteger calcResult(BigInteger p, int g, int random) {
        BigInteger pow = BigInteger.valueOf(g).pow(random);
        return pow.mod(p);
    }


    /**
     * 根据交换结果计算密钥
     *
     * @param p
     * @param random
     * @param result
     * @return
     */
    public static BigInteger getSecretKey(BigInteger p, int random, BigInteger result) {
        // 密钥
        BigInteger pow = result.pow(random);
        return pow.mod(p);
    }


    /**
     * 生成一个范围为 min - max 的随机整数
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRandomNum(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 交换流程
     *
     * @return
     */
    public static boolean dhProcess() {
        long sT = System.currentTimeMillis();
        int g = SimpleDhDemo.getRandomNum(2, 5);
        // 随机一个正质数
        BigInteger p = BigInteger.probablePrime(50, new Random());
        // 权衡计算时间，数字越大，计算时间越长
        int a = SimpleDhDemo.getRandomNum(1000, 100000);
        int b = SimpleDhDemo.getRandomNum(1000, 100000);
        System.out.println("g:" + g + " p:" + p + " a:" + a + " b:" + b);
        BigInteger aA = calcResult(p, g, a);
        BigInteger bB = calcResult(p, g, b);
        // 甲乙交换计算结果aA和bB
        System.out.println("甲的计算结果：" + aA);
        System.out.println("乙的计算结果：" + bB);

        // 双方分别计算其密钥
        BigInteger aSecretKey = getSecretKey(p, a, bB);
        BigInteger bSecretKey = getSecretKey(p, b, aA);
        System.out.println("甲获得密钥：" + aSecretKey);
        System.out.println("乙获得密钥：" + bSecretKey);
        long eT = System.currentTimeMillis();
        System.out.println("消耗时长：" + (eT - sT));
        return aSecretKey.equals(bSecretKey);
    }

    /**
     * 测试
     *
     * @param total
     */
    public static void testExecute(long total) {
        int success = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            if (dhProcess()) {
                success++;
            }
        }
        long end = System.currentTimeMillis();
        long interval = end - start;
        System.out.println("测试总次数：" + total + " 成功匹配次数：" + success);
        System.out.println("测试总时长：" + interval + "ms 平均单次耗时：" + (interval / total));
    }

    public static void main(String[] args) {
//        testExecute(1);
    }

}
