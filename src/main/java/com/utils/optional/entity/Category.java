package com.utils.optional.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author Wang Junwei
 * @Date 2023/2/8 11:05
 * @Description 商品分类
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private Long id;

    private String categoryName;

    private String categoryCode;


    public static Category FOOD = new Category(1L, "食品", "FOOD-0001");
    public static Category PET = new Category(2L, "宠物", "PET-0001");

    public static Map<Long, Category> map = Stream.of(FOOD, PET).collect(Collectors.toMap(Category::getId, v -> v));
}
