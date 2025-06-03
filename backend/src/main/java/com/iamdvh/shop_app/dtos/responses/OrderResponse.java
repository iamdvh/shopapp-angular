package com.iamdvh.shop_app.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse extends BaseResponse {
    @JsonProperty("user_id")
    Long userId;
    @JsonProperty("fullname")
    String fullName;
    @JsonProperty("phone_number")
    String phoneNumber;
    String address;
    String note;
    @JsonProperty("order_date")
    String orderDate;
    @JsonProperty("total_money")
    Float totalMoney;
    @JsonProperty("payment_method")
    String paymentMethod;
    @JsonProperty("tracking_number")
    String trackingNumber;
    @JsonProperty("shipping_method")
    String shippingMethod;
    @JsonProperty("shipping_address")
    String shippingAddress;
    private boolean active;
}
