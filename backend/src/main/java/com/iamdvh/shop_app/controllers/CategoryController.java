package com.iamdvh.shop_app.controllers;

import com.iamdvh.shop_app.dtos.CategoryDTO;
import com.iamdvh.shop_app.entities.Category;
import com.iamdvh.shop_app.services.ICategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//@Validated
public class CategoryController {
    ICategoryService categoryService;


    @PostMapping("")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessage = result.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .build();
        categoryService.createCategory(category);
        return ResponseEntity.ok().body("Insert category successfully.");
    }

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "limit") int limit
    ) {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id
    ) {
        Category category = categoryService.getCategory(id);
        return ResponseEntity.ok().body(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessage = result.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .build();
        categoryService.updateCategory(id, category);
        return ResponseEntity.ok().body("Update category successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().body("Delete category successfully.");
    }
}
