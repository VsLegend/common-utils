package com.utils.optional.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

import static com.utils.optional.entity.ProductPicture.PIC_1;
import static com.utils.optional.entity.ProductPicture.PIC_2;

/**
 * @Author Wang Junwei
 * @Date 2023/2/8 11:05
 * @Description 商品详情
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetail {

    private Long id;

    /**
     * 注意事项
     */
    private String description;

    /**
     * 商品图片
     */
    private List<ProductPicture> productPictures;

    public static ProductDetail FOOD_DETAIL = new ProductDetail(1L, "保质期：7天", Arrays.asList(PIC_1, PIC_2));

}
