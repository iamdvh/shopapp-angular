package com.iamdvh.shop_app.services.imp;

import com.iamdvh.shop_app.dtos.OrderDTO;
import com.iamdvh.shop_app.dtos.responses.OrderResponse;
import com.iamdvh.shop_app.entities.Order;
import com.iamdvh.shop_app.entities.OrderStatus;
import com.iamdvh.shop_app.entities.User;
import com.iamdvh.shop_app.exceptions.DataNotFoundException;
import com.iamdvh.shop_app.mapper.OrderMapper;
import com.iamdvh.shop_app.repositories.OrderRepository;
import com.iamdvh.shop_app.repositories.UserRepository;
import com.iamdvh.shop_app.services.IOrderService;
import com.iamdvh.shop_app.utils.FindBy;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService implements IOrderService {
    OrderRepository orderRepository;
    UserRepository userRepository;
    OrderMapper orderMapper;

    @Override
    public OrderResponse createOrder(OrderDTO orderDTO){
        User user = FindBy.findById(orderDTO.getUserId(), userRepository);
        Order order = orderMapper.toEntity(orderDTO);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        LocalDate shippingDate = order.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Date must be at lease today.");
        }
        order.setActive(true);
        orderRepository.save(order);
        OrderResponse result = orderMapper.toResponse(order);
//        result.set
        return result;
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderDTO orderDTO) {
        Order oldVersion = FindBy.findById(id, orderRepository);
        Order newVersion = orderMapper.toEntityUpdate(oldVersion,orderDTO);



        return null;
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = FindBy.findById(id, orderRepository);
        order.setActive(false);
        orderRepository.save(order);
    }

    @Override
    public OrderResponse getOrder(Long id) {
        OrderResponse result = FindBy.findByIdToResponse(id, orderRepository, orderMapper::toResponse);
        return result;
    }


    @Override
    public List<OrderResponse> getAllOrder(Long userId) {
        List<OrderResponse> result = orderRepository.findByUserId(userId)
                .stream().map(orderMapper::toResponse).toList();

        return result;
    }
}
