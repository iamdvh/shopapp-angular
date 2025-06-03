package com.iamdvh.shop_app.mapper;

import com.iamdvh.shop_app.dtos.OrderDTO;
import com.iamdvh.shop_app.dtos.responses.OrderResponse;
import com.iamdvh.shop_app.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(OrderDTO dto);
    Order toEntityUpdate(@MappingTarget Order oldVersion, OrderDTO newVersion);
    @Mapping(source = "user.id" , target = "userId")
    OrderResponse toResponse(Order order);
}