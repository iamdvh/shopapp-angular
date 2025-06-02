package com.iamdvh.shop_app.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iamdvh.shop_app.models.Product;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDTO {
    @JsonProperty(value = "product_id")
    @Size(min = 1, message = "Product's Id must be > 0.")
    Long productId;
    @Size(min = 5, max = 200, message = "Image's name.")
    @JsonProperty(value = "image_url")
    String imageUrl;
}
