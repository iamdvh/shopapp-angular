package com.iamdvh.shop_app.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class Product extends Base {
    @Column(name = "name", nullable = false, length = 350)
    String name;
    Float price;
    @Column(name = "thumbnail", length = 300)
    String thumbnail;
    @Column(name = "description")
    String description;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
}
