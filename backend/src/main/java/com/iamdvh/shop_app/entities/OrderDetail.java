package com.iamdvh.shop_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_details")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDetail extends BasePrimaryKey {
    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
    Float price;
    @Column(name = "number_of_product")
    Integer numberOfProducts;
    @Column(name = "total_money")
    Float totalMoney;
    String color;
}
