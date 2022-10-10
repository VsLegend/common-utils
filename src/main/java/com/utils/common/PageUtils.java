package com.utils.common;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.utils.exception.Back;

import java.util.List;

/**
 * @Author Wang Junwei
 * @Date 2022/7/1 10:00
 * @Description 基于PageHelper分页插件
 */
public class PageUtils {

    public static void startPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
    }

    public static <T> PageInfo<T> endPage(List<T> resultList) {
        return new PageInfo<>(resultList);
    }


    public static <T> Back<PageInfo<T>> pageBack(List<T> resultList) {
        PageInfo<T> pageInfo = new PageInfo<>(resultList);
        return Back.success(pageInfo);
    }
}
