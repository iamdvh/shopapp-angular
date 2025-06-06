package com.iamdvh.shop_app.services;

import com.iamdvh.shop_app.dtos.OrderDTO;
import com.iamdvh.shop_app.dtos.responses.OrderResponse;

import java.util.List;

public interface IOrderDetailService {
    OrderResponse createOrderDetail(OrderDTO orderDTO);

    OrderResponse updateOrderDetail(Long id, OrderDTO orderDTO);

    void deleteOrderDetail(Long id);

    OrderResponse getOrderDetail(Long id);

    List<OrderResponse> getOrderDetails(Long userId);
}
