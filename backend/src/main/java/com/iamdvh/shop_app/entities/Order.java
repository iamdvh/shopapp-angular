package com.iamdvh.shop_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order extends BasePrimaryKey {
    @Column(name = "fullname", length = 100)
    String fullName;
    @Column(name = "phone_number", nullable = false, length = 10)
    String phoneNumber;
    @Column(length = 200, nullable = false)
    String address;
    @Column(length = 100)
    String note;
    @Column(name = "order_date")
    Date orderDate;
    @Column(name = "status")
    String status;
    @Column(name = "total_money")
    String totalMoney;
    @Column(name = "active")
    boolean active;
    @Column(name = "shipping_method", length = 100)
    String shippingMethod;
    @Column(name = "shipping_address", length = 200)
    String shippingAddress;
    @Column(name = "tracking_number", length = 100)
    String trackingNumber;
    @Column(name = "payment_method", length = 100)
    String paymentMethod;
    Date shippingDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
