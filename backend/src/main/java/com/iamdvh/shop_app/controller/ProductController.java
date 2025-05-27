package com.iamdvh.shop_app.controller;

import ch.qos.logback.core.util.StringUtil;
import com.iamdvh.shop_app.dtos.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @GetMapping("")
    public ResponseEntity<String> getAllProducts(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "limit") int limit
    ) {
        return ResponseEntity.ok().body(String.format("/api/v1/products?%d&%d", page, limit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable String id) {
        return ResponseEntity.ok().body("update");
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insertProduct(@Valid @ModelAttribute ProductDTO productDTO,
                                           BindingResult result) throws IOException {
        if (result.hasErrors()) {
            List<String> errorMessage = result.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        List<MultipartFile> files = productDTO.getFiles();
        files = files == null ? new ArrayList<>() : files;
        for (MultipartFile file : files) {
            if(file.getSize() == 0){
                continue;
            }

            if (file.getSize() > 10 * 1024 * 1024) {
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large");
            }
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image.");
            }
            String thumbnail =  storeFile(file);

        }



        return ResponseEntity.ok().body("Product created Successfully");
    }

    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        Path uploadDir = Paths.get("uploads");
        if(!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        Path path = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        return ResponseEntity.ok().body("delete");
    }
}
//{
//        "name": "M",
//        "price": "-999.999",
//        "thumbnail": "abc",
//        "description": "Macbook",
//        "category_id": "123"
//        }