package com.iamdvh.shop_app.controllers;

import com.iamdvh.shop_app.dtos.OrderDTO;
import com.iamdvh.shop_app.dtos.responses.OrderResponse;
import com.iamdvh.shop_app.entities.OrderDetail;
import com.iamdvh.shop_app.services.imp.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDTO request, BindingResult result) {
        try {

            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return ResponseEntity.ok(orderService.createOrder(request));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrders(@PathVariable("user_id") Long userId) {
        try {
            List<OrderResponse> result = orderService.getAllOrder(userId);
            return ResponseEntity.ok(result);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(orderService.getOrder(id));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") Long id) {
        try {
            return null;
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
