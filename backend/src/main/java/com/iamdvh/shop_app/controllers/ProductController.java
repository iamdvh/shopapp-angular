package com.iamdvh.shop_app.controllers;

import com.github.javafaker.Faker;
import com.iamdvh.shop_app.contants.ApplicationSystemContant;
import com.iamdvh.shop_app.dtos.ProductDTO;
import com.iamdvh.shop_app.dtos.ProductImageDTO;
import com.iamdvh.shop_app.exceptions.DataNotFoundException;
import com.iamdvh.shop_app.exceptions.InvalidParam;
import com.iamdvh.shop_app.entities.Product;
import com.iamdvh.shop_app.entities.ProductImage;
import com.iamdvh.shop_app.dtos.responses.ProductListResponse;
import com.iamdvh.shop_app.dtos.responses.ProductResponse;
import com.iamdvh.shop_app.services.BaseRedisService;
import com.iamdvh.shop_app.services.IProductService;
import io.lettuce.core.RedisConnectionException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
  IProductService productService;
  BaseRedisService baseRedisService;
  String PRODUCT_HASH_KEY = "products";

  @GetMapping("")
  public ResponseEntity<ProductListResponse> getAllProducts(
    @RequestParam(value = "page") int page,
    @RequestParam(value = "limit") int limit
  ) {
    // Validate input
    if (page < 0 || limit <= 0) {
      throw new IllegalArgumentException("Page must be >= 0 and limit must be > 0");
    }

    // Tạo PageRequest
    PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
    String sort = pageRequest.getSort().toString().replaceAll(": ", "_").toLowerCase(); // Chuẩn hóa sort
    String field = String.format("product:%d:%d:%s", page, limit, sort); // Ví dụ: product:0:10:createdat_desc

    // Thử lấy từ Redis
    Object cachedData = baseRedisService.hashGet(PRODUCT_HASH_KEY, field);
    List<ProductResponse> products;
    int totalPages = 0;

    if (cachedData instanceof List) {
      products = (List<ProductResponse>) cachedData;
    } else {
      // Lấy từ database nếu không có trong Redis
      Page<ProductResponse> productPage = productService.getAllProduct(pageRequest);
      totalPages = productPage.getTotalPages();
      products = productPage.getContent();
      // Lưu vào Redis
      if (!products.isEmpty()) { // Chỉ lưu nếu có dữ liệu
        baseRedisService.hashSet(PRODUCT_HASH_KEY, field, products);
        baseRedisService.setTimeToLive(PRODUCT_HASH_KEY, 1); // TTL 1 ngày
      }
    }

    return ResponseEntity.ok(ProductListResponse.builder()
      .products(products)
      .totalPages(totalPages)
      .build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateProduct(@PathVariable String id) {
    return ResponseEntity.ok().body("update");
  }

  @PostMapping()
  public ResponseEntity<?> createProduct(@Valid @ModelAttribute ProductDTO productDTO,
                                         BindingResult result) throws IOException, DataNotFoundException, InvalidParam {
    if (result.hasErrors()) {
      List<String> errorMessage = result.getFieldErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
    Product saved = productService.createProduct(productDTO);

    return ResponseEntity.ok().body(saved);
  }


  @PostMapping(value = "/uploads/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> upload(@PathVariable Long productId, List<MultipartFile> files) {
    try {
      if (files.size() > ApplicationSystemContant.MAXIMUM_IMAGE_PRODUCT) {
        return ResponseEntity.badRequest().body("File is too large");
      }
      List<ProductImage> productImages = new ArrayList<>();
      Product existingProduct = productService.getProductById(productId);

      files = files == null ? new ArrayList<>() : files;
      for (MultipartFile file : files) {
        if (file.getSize() == 0) {
          continue;
        }

        if (file.getSize() > 10 * 1024 * 1024) {
          return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
          return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image.");
        }
        String fileName = storeFile(file);
        ProductImageDTO productImageDTO = new ProductImageDTO();
        productImageDTO.setProductId(productId);
        productImageDTO.setImageUrl(fileName);

        ProductImage productImage = productService.createProductImage(productId, ProductImageDTO.builder()
          .productId(existingProduct.getId())
          .imageUrl(fileName)
          .build());
        productImages.add(productImage);
      }
      return ResponseEntity.ok().body(productImages);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  private String storeFile(MultipartFile file) throws IOException {
    if (!isImageFile(file) && file.getOriginalFilename() != null) {
      throw new IOException("Invalid.");
    }

    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
    Path uploadDir = Paths.get("uploads");
    if (!Files.exists(uploadDir)) {
      Files.createDirectories(uploadDir);
    }
    Path path = Paths.get(uploadDir.toString(), uniqueFileName);
    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

    return uniqueFileName;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getProductById(@PathVariable Long id) throws DataNotFoundException {
    return ResponseEntity.ok().body(productService.getProductById(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteProduct(@PathVariable String id) {
    return ResponseEntity.ok().body("delete");
  }

  private boolean isImageFile(MultipartFile file) {
    String contentType = file.getContentType();
    return contentType != null && contentType.startsWith("image/");
  }

  @PostMapping("/generateFakerProducts")
  public ResponseEntity<?> generateFakerProducts() {
    Faker faker = new Faker();
    for (int i = 0; i < 1000000; i++) {
      String productName = faker.commerce().productName();
      if (productService.existsByName(productName)) {
        continue;
      }
      ProductDTO productDTO = ProductDTO.builder()
        .name(productName)
        .price((float) faker.number().numberBetween(0, 90000000))
        .description(faker.lorem().sentence())
        .categoryId((long) faker.number().numberBetween(1, 3))
        .build();
      try {

        productService.createProduct(productDTO);
      } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
    }
    return ResponseEntity.ok().body("Generate faker products successfully");
  }
}
