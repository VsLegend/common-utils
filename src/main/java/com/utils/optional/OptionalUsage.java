package com.utils.optional;

import com.utils.optional.entity.*;

import java.util.List;
import java.util.Optional;

/**
 * @Author Wang Junwei
 * @Date 2023/2/8 10:49
 * @Description Optional业务实践
 */
public class OptionalUsage {

    public static void main(String[] args) {

    }

    /**
     * 1. if-else分支操作
     *
     * @param productId
     * @return
     */
    public static Product selectProductOtherwiseDefault(Long productId) {
        Optional<Product> optional = Optional.ofNullable(getProduct(productId));
        optional.ifPresent(OptionalUsage::setProductInfo);
        return optional.orElse(getDefaultProduct());
//        return optional.orElseThrow(BusinessException::new);
//        return optional.orElseGet(OptionalUsage::getDefaultProduct);
    }

    public static Product selectProductOtherwiseDefault1(Long productId) {
        Product product = getProduct(productId);
        if (product != null) {
            setProductInfo(product);
        } else {
            product = getDefaultProduct();
//            throw new BusinessException();
        }
        return product;
    }

    /**
     * 2. 对象属性非空校验
     */
    public static List<ProductPicture> getProductPicture(Product product) {
        Optional<Product> optional = Optional.ofNullable(product);
        return optional
                .map(Product::getProductDetail)
                .map(ProductDetail::getProductPictures)
                .orElse(null);
    }

    /**
     * 非空校验
     *
     * @param product
     * @return
     */
    public static List<ProductPicture> getProductPicture1(Product product) {
        if (product != null) {
            ProductDetail productDetail = product.getProductDetail();
            if (productDetail != null) {
                return productDetail.getProductPictures();
            }
        }
        return null;
    }


    /**
     * 3. 嵌套非空校验
     */
    public static void setProductInfo(Product product) {
        Optional<Product> optional = Optional.ofNullable(product);
        optional.ifPresent(product1 -> {
            // 这里的getBrand方法在业务中一般是sql查询
            product1.setBrand(Optional.ofNullable(product1.getBrandId()).map(OptionalUsage::getBrand).orElse(null));
            product1.setCategory(Optional.ofNullable(product1.getCategoryId()).map(OptionalUsage::getCategory).orElse(null));
        });
    }

    /**
     * 非空校验
     *
     * @param product
     */
    public static void setProductInfo1(Product product) {
        if (product != null) {
            Long brandId = product.getBrandId();
            Long categoryId = product.getCategoryId();
            if (brandId != null) {
                product.setBrand(OptionalUsage.getBrand(brandId));
            }
            if (categoryId != null) {
                product.setCategory(OptionalUsage.getCategory(categoryId));
            }
        }
    }


    /**
     * 4. 其它校验与非空校验组合
     *
     * @param product
     */
    public static boolean checkProductIntegrityThenUpdate(Product product) {
        Optional<Product> optional = Optional.ofNullable(product)
                .filter(product1 -> null != product.getProductDetail() && null != product.getCategory() && anyOtherCheck());
        optional.ifPresent(OptionalUsage::updateIntegrityProductInfo);
        return optional.isPresent();
    }

    public static boolean checkProductIntegrityThenUpdate1(Product product) {
        boolean check = product != null && null != product.getProductDetail() && null != product.getCategory() && anyOtherCheck();
        if (check) {
            updateIntegrityProductInfo(product);
        }
        return check;
    }

    private static void updateIntegrityProductInfo(Product product) {
        product.setIntegrity(true);
    }

    private static boolean anyOtherCheck() {
        return true;
    }

    private static Product getProduct(Long productId) {
        return null;
    }

    private static Product getDefaultProduct() {
        return null;
    }

    /**
     * 获取商品详情
     *
     * @param productId
     * @return
     */
    public static ProductDetail getProductDetail(Long productId) {
        // mapper
        ProductDetail foodDetail = ProductDetail.FOOD_DETAIL;
        return foodDetail.getId().equals(productId) ? foodDetail : null;
    }

    /**
     * 品牌
     *
     * @param brandId
     * @return
     */
    public static Brand getBrand(Long brandId) {
        // mapper
        return Brand.map.get(brandId);
    }

    /**
     * 分类
     *
     * @param categoryId
     * @return
     */
    public static Category getCategory(Long categoryId) {
        // mapper
        return Category.map.get(categoryId);
    }

}
