package com.iamdvh.shop_app.controllers;

import com.iamdvh.shop_app.dtos.OrderDetailsDTO;
import com.iamdvh.shop_app.services.imp.OrderDetailsService;
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
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("${api.prefix}/order-details")
public class OrderDetailsController {
    OrderDetailsService orderDetailsService;

    @PostMapping
    public ResponseEntity<?> createOrderDetails(@RequestBody @Valid OrderDetailsDTO orderDetailsDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            return ResponseEntity.ok().body(orderDetailsService.createOrderDetail(orderDetailsDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/order/{order_id}")
    public ResponseEntity<?> getOrderDetails(@PathVariable("order_id") Long orderId) {
        try {
            return ResponseEntity.ok().body(orderDetailsService.getOrderDetails(orderId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(orderDetailsService.getOrderDetail(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetails(@PathVariable("id") Long id, @RequestBody @Valid OrderDetailsDTO orderDetailsDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            return ResponseEntity.ok().body(orderDetailsService.updateOrderDetail(id,orderDetailsDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetails(@PathVariable("id") Long id) {
        try {
            orderDetailsService.deleteOrderDetail(id);
            return ResponseEntity.ok().body("Order detail deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
