package com.utils.list.page;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author Wang Junwei
 * @Date 2023/2/2 14:27
 * @Description pagehelper物理分页相关
 */
public class ServicePageUtils {

    /**
     * 物理分页偏移量计算
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public static int getSkip(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 20;
        }
        return (pageNum - 1) * pageSize;
    }

    /**
     * 物理分页，生成分页信息
     *
     * @param list
     * @param pageNum
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> PageInfo<T> pageList(List<T> list, Integer pageNum, Integer pageSize) {
        int total = list.size();
        if (total > pageSize) {
            int toIndex = pageSize * pageNum;
            if (toIndex > total) {
                toIndex = total;
            }
            list = list.subList(pageSize * (pageNum - 1), toIndex);
        }
        Page<T> page = new Page<>(pageNum, pageSize);
        page.addAll(list);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        return new PageInfo<>(page);
    }

}
