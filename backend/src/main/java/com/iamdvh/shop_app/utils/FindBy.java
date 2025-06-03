package com.iamdvh.shop_app.utils;

import com.iamdvh.shop_app.exceptions.DataNotFoundException;
import com.iamdvh.shop_app.mapper.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;


public class FindBy {

    public static <ID, E> E findById(ID id, JpaRepository<E, ID> repository) {
        return repository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Entity not found"));
    }

    public static <ID, E, R> R findByIdToResponse(ID id, JpaRepository<E, ID> repository, Mapper<E, R> mapper) {
        E entity = repository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Entity not found"));
        R result = mapper.toResponse(entity);
        return result;
    }
}
