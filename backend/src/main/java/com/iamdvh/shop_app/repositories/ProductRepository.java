package com.iamdvh.shop_app.repositories;

import com.iamdvh.shop_app.models.Product;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
    Page<Product> findAll(Pageable pageable);
}
