package com.iamdvh.shop_app.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotEmpty(message = "Product name cannot be empty.")
            @Size(min = 3, max = 200, message = "Title must be between 3 and 200 character.")
    String name;
    @Min(value= 0, message = "Price must be greater than or equal to 0.")
    @Max(value= 10000000, message = "Price must be less than or equal to 10,000,000.")
    Float price;
    String thumbnail;
    String description;
    @JsonProperty("category_id")
    String categoryId;
    List<MultipartFile>  files;
}
