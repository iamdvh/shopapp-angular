package com.iamdvh.shop_app.repositories;

import com.iamdvh.shop_app.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
