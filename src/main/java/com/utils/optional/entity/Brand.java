package com.utils.optional.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author Wang Junwei
 * @Date 2023/2/8 11:05
 * @Description 品牌信息
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Brand {

    private Long id;

    private String brandName;

    public static Brand FOOD = new Brand(1L, "XXX食物品牌");
    public static Brand PET = new Brand(2L, "XXX宠物品牌");

    public static Map<Long, Brand> map = Stream.of(FOOD, PET).collect(Collectors.toMap(Brand::getId, v -> v));
}
