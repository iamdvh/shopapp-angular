package com.iamdvh.shop_app.responses;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class ProductListResponse {
    List<ProductResponse> products;
    int totalPages;
}
