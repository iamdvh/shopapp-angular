package com.iamdvh.shop_app.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailsResponse {
    Long id;
    @JsonProperty("order_id")
    Long orderId;
    @JsonProperty("product_id")
    Long productId;
    Float price;
    @JsonProperty("number_of_products")
    Integer numberOfProducts;
    @Min(value = 0, message = "Price must be >= 0.")
    Float totalMoney;
    String color;
}
