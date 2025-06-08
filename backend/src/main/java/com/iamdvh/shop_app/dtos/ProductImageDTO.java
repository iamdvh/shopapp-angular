package com.iamdvh.shop_app.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
