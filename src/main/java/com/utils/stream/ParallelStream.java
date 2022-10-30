package com.utils.stream;

/**
 * @Author Wang Junwei
 * @Date 2022/10/29 14:15
 * @Description 并行流
 */
public class ParallelStream {

    public static void main(String[] args) {
        char[] chars = {'c', 'b'};
        String s1 = new String(chars);
        String s2 = chars.toString();
        System.out.println(s1);
        System.out.println(s2);
    }
}
