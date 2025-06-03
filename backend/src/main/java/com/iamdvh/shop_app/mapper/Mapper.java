package com.iamdvh.shop_app.mapper;
@FunctionalInterface
public interface Mapper <E, R>{
    R toResponse(E entity);
}
