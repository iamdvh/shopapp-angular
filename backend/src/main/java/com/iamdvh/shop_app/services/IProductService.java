package com.iamdvh.shop_app.services;

import com.iamdvh.shop_app.dtos.ProductDTO;
import com.iamdvh.shop_app.dtos.ProductImageDTO;
import com.iamdvh.shop_app.dtos.UserDTO;
import com.iamdvh.shop_app.exceptions.DataNotFoundException;
import com.iamdvh.shop_app.exceptions.InvalidParam;
import com.iamdvh.shop_app.models.Product;
import com.iamdvh.shop_app.models.ProductImage;
import com.iamdvh.shop_app.models.User;
import com.iamdvh.shop_app.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws DataNotFoundException;

    Product updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException;

    void deleteProduct(Long id);

    Product getProductById(Long id) throws DataNotFoundException;

    Page<ProductResponse> getAllProduct(PageRequest pageRequest);

    boolean existsByName(String name);

    ProductImage createProductImage(Long productId,
                                    ProductImageDTO productImageDTO) throws DataNotFoundException, InvalidParam;

}
