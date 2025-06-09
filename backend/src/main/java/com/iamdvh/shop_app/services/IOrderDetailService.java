package com.iamdvh.shop_app.services;

import com.iamdvh.shop_app.dtos.OrderDetailsDTO;
import com.iamdvh.shop_app.dtos.responses.OrderDetailsResponse;

import java.util.List;

public interface IOrderDetailService {
    OrderDetailsResponse createOrderDetail(OrderDetailsDTO orderDetailsDTO);

    OrderDetailsResponse updateOrderDetail(Long id, OrderDetailsDTO orderDetailsDTO);

    void deleteOrderDetail(Long id);

    OrderDetailsResponse

    getOrderDetail(Long id);

    List<OrderDetailsResponse> getOrderDetails(Long orderId);
}
