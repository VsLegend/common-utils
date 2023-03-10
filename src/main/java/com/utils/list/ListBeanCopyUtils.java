package com.utils.list;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @Author Wang Junwei
 * @Date 2023/2/2 15:29
 * @Description 列表bean转换
 */
public class ListBeanCopyUtils {


    /**
     * 列表内容转换
     *
     * @param tClass
     * @param list
     * @param <T>
     * @param <U>
     * @return
     */
    public static <T, U> List<T> convertList(List<U> list, Class<T> tClass) {
        List<T> ts = new ArrayList<>(list.size());
        try {
            for (U u : list) {
                ts.add(BeanUtil.toBean(u, tClass));
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return ts;
    }

    /**
     * 列表内容转换
     *
     * @param list
     * @param function
     * @param <T>
     * @param <U>
     * @return
     */
    public static <T, U> List<T> convertListByFunction(List<U> list, Function<U, T> function) {
        if (ObjectUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<T> ts = new ArrayList<>(list.size());
        try {
            for (U u : list) {
                ts.add(function.apply(u));
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return ts;
    }

}
