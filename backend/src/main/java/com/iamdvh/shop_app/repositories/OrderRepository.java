package com.iamdvh.shop_app.repositories;

import com.iamdvh.shop_app.entities.Order;
import com.iamdvh.shop_app.entities.OrderDetail;
import com.iamdvh.shop_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    Long user(User user);
}
