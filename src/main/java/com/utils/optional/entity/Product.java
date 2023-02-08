package com.utils.optional.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Wang Junwei
 * @Date 2023/2/8 11:04
 * @Description 商品信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Long id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 商品详情
     */
    private ProductDetail productDetail;

    private Long categoryId;
    /**
     * 分类信息
     */
    private Category category;


    private Long brandId;
    /**
     * 品牌
     */
    private Brand brand;

    private boolean integrity;

}
