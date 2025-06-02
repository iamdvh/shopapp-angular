package com.iamdvh.shop_app.services;

import com.iamdvh.shop_app.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(Category category);
    Category getCategory(Long id);
    List<Category> getAllCategories();
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);
}
