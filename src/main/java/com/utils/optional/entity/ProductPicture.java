package com.utils.optional.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Wang Junwei
 * @Date 2023/2/8 11:05
 * @Description 商品图片信息展示
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPicture {

    private Long id;

    private String description;

    private String picUrl;


    public static ProductPicture PIC_1 = new ProductPicture(1L, "图片1", "www.xx.com/url");
    public static ProductPicture PIC_2 = new ProductPicture(1L, "图片2", "www.xx.com/url");

}
