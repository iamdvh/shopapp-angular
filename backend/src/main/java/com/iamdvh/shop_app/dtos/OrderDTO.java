package com.iamdvh.shop_app.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDTO {
    @JsonProperty("user_id")
    @Min(value = 1, message = "User's ID must be > 0.")
    Long userId;
    @JsonProperty("fullname")
    String fullName;
    String email;
    @NotBlank(message = "Phone number is required.")
    @JsonProperty("phone_number")
    String phoneNumber;
    @NotBlank(message = "Address is required.")
    String address;
    String note;
    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be >= 0.")
    Float totalMoney;
    @JsonProperty("shipping_date")
    Date shippingDate;
    @JsonProperty("shipping_method")
    String shippingMethod;
    @JsonProperty("shipping_address")
    String shippingAddress;
    @JsonProperty("payment_method")
    String paymentMethod;
}
