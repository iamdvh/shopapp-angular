package com.iamdvh.shop_app.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class ProductResponse extends BaseResponse{
    Long id;
    String name;
    Float price;
    String thumbnail;
    String description;
    @JsonProperty("category_id")
    Long categoryId;
}
