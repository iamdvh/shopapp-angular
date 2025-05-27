package com.iamdvh.shop_app.controller;

import com.iamdvh.shop_app.dtos.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
//@Validated
public class CategoryController {
    @GetMapping("")
    public ResponseEntity<String> getAllCategories(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "limit") int limit
    ) {
        return ResponseEntity.ok().body(String.format("/api/v1/categories?%d&%d", page, limit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable String id) {
        return ResponseEntity.ok().body("update");
    }

    @PostMapping("")
    public ResponseEntity<?> insertCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result) {
        if(result.hasErrors()) {
           List<String> errorMessage =  result.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
           return ResponseEntity.badRequest().body(errorMessage);
        }

        return ResponseEntity.ok().body("insert");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable String id) {
        return ResponseEntity.ok().body("delete");
    }
}
