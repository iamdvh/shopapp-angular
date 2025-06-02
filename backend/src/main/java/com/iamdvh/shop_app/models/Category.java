package com.iamdvh.shop_app.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category extends BasePrimaryKey {
    @Column(name = "name", nullable = false)
    String name;
}
