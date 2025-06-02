package com.iamdvh.shop_app.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class BaseResponse {
    @JsonProperty(value = "created_at")
    LocalDateTime createdAt;
    @JsonProperty(value = "updated_at")
    LocalDateTime updatedAt;
}
