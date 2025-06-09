package com.iamdvh.shop_app.services.imp;

import com.iamdvh.shop_app.dtos.OrderDTO;
import com.iamdvh.shop_app.dtos.OrderDetailsDTO;
import com.iamdvh.shop_app.dtos.responses.OrderDetailsResponse;
import com.iamdvh.shop_app.dtos.responses.OrderResponse;
import com.iamdvh.shop_app.entities.Order;
import com.iamdvh.shop_app.entities.OrderDetail;
import com.iamdvh.shop_app.entities.Product;
import com.iamdvh.shop_app.mapper.OrderDetailMapper;
import com.iamdvh.shop_app.repositories.OrderDetailRepository;
import com.iamdvh.shop_app.repositories.OrderRepository;
import com.iamdvh.shop_app.repositories.ProductRepository;
import com.iamdvh.shop_app.services.IOrderDetailService;
import com.iamdvh.shop_app.utils.FindBy;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class OrderDetailsService implements IOrderDetailService {
    OrderDetailRepository orderDetailRepository;
    OrderRepository orderRepository;
    ProductRepository productRepository;
    OrderDetailMapper orderDetailMapper;


    @Override
    public OrderDetailsResponse createOrderDetail(OrderDetailsDTO orderDetailsDTO) {
        Order order = FindBy.findById(orderDetailsDTO.getOrderId(), orderRepository);
        Product product = FindBy.findById(orderDetailsDTO.getProductId(), productRepository);
        OrderDetail orderDetail = orderDetailMapper.toEntity(orderDetailsDTO);
        orderDetail.setProduct(product);
        orderDetail.setOrder(order);
        OrderDetail saved = orderDetailRepository.save(orderDetail);
        OrderDetailsResponse result = orderDetailMapper.toResponse(saved);
        return result;
    }

    @Override
    public OrderDetailsResponse updateOrderDetail(Long id, OrderDetailsDTO orderDetailsDTO) {
        Order order = FindBy.findById(orderDetailsDTO.getOrderId(), orderRepository);
        Product product = FindBy.findById(orderDetailsDTO.getProductId(), productRepository);
        OrderDetail orderDetail = FindBy.findById(id, orderDetailRepository);
        OrderDetail newVersion = orderDetailMapper.toEntityUpdate(orderDetail, orderDetailsDTO);
        newVersion.setProduct(product);
        newVersion.setOrder(order);
        OrderDetail saved = orderDetailRepository.save(newVersion);
        OrderDetailsResponse result = orderDetailMapper.toResponse(saved);
        return result;
    }

    @Override
    public void deleteOrderDetail(Long id) {
        FindBy.findById(id, orderDetailRepository);
        orderDetailRepository.deleteById(id);
    }

    @Override
    public OrderDetailsResponse getOrderDetail(Long id) {
        OrderDetail orderDetail = FindBy.findById(id, orderDetailRepository);
        return orderDetailMapper.toResponse(orderDetail);
    }

    @Override
    public List<OrderDetailsResponse> getOrderDetails(Long orderId) {
        Order order = FindBy.findById(orderId, orderRepository);
        return orderDetailRepository.findByOrderId(order.getId())
                .stream()
                .map(orderDetailMapper::toResponse).toList();
    }
}
