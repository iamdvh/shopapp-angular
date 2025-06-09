package com.iamdvh.shop_app.mapper;

import com.iamdvh.shop_app.dtos.OrderDetailsDTO;
import com.iamdvh.shop_app.dtos.responses.OrderDetailsResponse;
import com.iamdvh.shop_app.entities.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetail toEntity(OrderDetailsDTO dto);
    OrderDetail toEntityUpdate(@MappingTarget OrderDetail oldVersion, OrderDetailsDTO newVersion);
    @Mapping(source = "order.id" , target = "orderId")
    @Mapping(source = "product.id" , target = "productId")
    OrderDetailsResponse toResponse(OrderDetail entity);
}