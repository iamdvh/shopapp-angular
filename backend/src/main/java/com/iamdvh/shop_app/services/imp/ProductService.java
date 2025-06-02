package com.iamdvh.shop_app.services.imp;

import com.iamdvh.shop_app.contants.ApplicationSystemContant;
import com.iamdvh.shop_app.dtos.ProductDTO;
import com.iamdvh.shop_app.dtos.ProductImageDTO;
import com.iamdvh.shop_app.exceptions.DataNotFoundException;
import com.iamdvh.shop_app.exceptions.InvalidParam;
import com.iamdvh.shop_app.models.Category;
import com.iamdvh.shop_app.models.Product;
import com.iamdvh.shop_app.models.ProductImage;
import com.iamdvh.shop_app.repositories.CategoryRepository;
import com.iamdvh.shop_app.repositories.ProductImageRepository;
import com.iamdvh.shop_app.repositories.ProductRepository;
import com.iamdvh.shop_app.responses.ProductResponse;
import com.iamdvh.shop_app.services.IProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService implements IProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ProductImageRepository productImageRepository;


    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find category with id: " + productDTO.getCategoryId()));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .thumbnail("")
                .category(category)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find product with id: " + id));
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find category with id: " + productDTO.getCategoryId()));
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setThumbnail(productDTO.getThumbnail());
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(Long id) throws DataNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find product with id: " + id));
        return product;
    }

    @Override
    public Page<ProductResponse> getAllProduct(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest)
                .map(i -> {
                            ProductResponse productResponse = ProductResponse.builder()
                                    .id(i.getId())
                                    .name(i.getName())
                                    .price(i.getPrice())
                                    .description(i.getDescription())
                                    .thumbnail(i.getThumbnail())
                                    .build();
                            productResponse.setCreatedAt(i.getCreatedAt());
                            productResponse.setUpdatedAt(i.getUpdatedAt());
                            return productResponse;
                        }
                );
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }


    @Override
    public ProductImage createProductImage(Long productId,
                                           ProductImageDTO productImageDTO) throws DataNotFoundException, InvalidParam {
        Product existingProduct = getProductById(productId);
        ProductImage productImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        // Không cho insert 5 ảnh trong 1 sản phầm
        int size = productImageRepository.findByProductId(productId).size();
        if (size >= ApplicationSystemContant.MAXIMUM_IMAGE_PRODUCT) {
            throw new InvalidParam("Number of images must be <= 5");
        }

        return productImageRepository.save(productImage);
    }



}
