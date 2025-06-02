package com.iamdvh.shop_app.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "product_images")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class ProductImage extends  BasePrimaryKey {
    @Column(name = "image_url", length = 300)
    String imageUrl;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
}
