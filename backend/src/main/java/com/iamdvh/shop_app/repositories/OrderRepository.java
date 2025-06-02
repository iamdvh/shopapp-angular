package com.iamdvh.shop_app.repositories;

import com.iamdvh.shop_app.models.Order;
import com.iamdvh.shop_app.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<OrderDetail> findByUserId(Long userId);
}
