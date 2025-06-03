package com.iamdvh.shop_app.services;

import com.iamdvh.shop_app.dtos.OrderDTO;
import com.iamdvh.shop_app.dtos.responses.OrderResponse;

import java.util.List;

public interface IOrderService {
    OrderResponse createOrder(OrderDTO orderDTO);

    OrderResponse updateOrder(Long id, OrderDTO orderDTO);

    void deleteOrder(Long id);

    OrderResponse getOrder(Long id);

    List<OrderResponse> getAllOrder(Long userId);
}
