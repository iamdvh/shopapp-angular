package com.iamdvh.shop_app.dtos;

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
public class OrderDetailsDTO {
    @Min(value = 1, message = "Order's Id must be > 0.")
    @JsonProperty("order_id")
    Long orderId;
    @Min(value = 1, message = "Product's Id must be > 0.")
    @JsonProperty("product_id")
    Long productId;
    @Min(value = 0, message = "Price must be >= 0.")
    Float price;
    @JsonProperty("number_of_products")
    @Min(value = 1, message = "Price must be > 0.")
    Integer numberOfProducts;
    @JsonProperty("total_money")
    @Min(value = 0, message = "Price must be >= 0.")
    Float totalMoney;
    String color;
}
