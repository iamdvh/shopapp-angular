package com.iamdvh.shop_app.repositories;

import com.iamdvh.shop_app.models.Category;
import com.iamdvh.shop_app.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Long productId);
}
