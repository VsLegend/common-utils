package com.utils.list;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author Wang Junwei
 * @Date 2022/3/31 15:13
 * @Description 列表工具
 */
public class ListUtil {

    /**
     * 分割数组
     *
     * @param list 指定列表
     * @param maxNum 每一段分组的数量
     * @param <T>  列表类型
     * @return
     */
    public static <T> Collection<Collection<T>> segmentList(Collection<T> list, int maxNum) {
        // 最大分组数量
        int maxSize = calcSegmentSize(list.size(), maxNum);
        return Stream.iterate(0, n -> n + 1)
                .limit(maxSize)
                .parallel()
                .map(a -> list.stream().skip(a * maxNum).limit(maxNum).parallel().collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    /**
     * 计算切分次数
     */
    public static Integer calcSegmentSize(int size, int maxNum) {
        return (size + maxNum - 1) / maxNum;
    }

}
