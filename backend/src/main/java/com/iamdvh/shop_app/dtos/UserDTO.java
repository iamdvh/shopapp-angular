package com.iamdvh.shop_app.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotEmpty(message = "Category name cannot be empty.")
    @JsonProperty("fullname")
    String fullname;
    String address;
    @NotBlank(message = "Phone number is required.")
    @JsonProperty("phone_number")
    String phoneNumber;
    @NotBlank(message = "Password cannot be blank.")
    String password;
    @JsonProperty("retype_password")
    String retypePassword;
    @JsonProperty("day_of_birth")
    LocalDateTime dateOfBirth;
    @JsonProperty("facebook_account_id")
    int facebookAccountId;
    @JsonProperty("google_account_id")
    int googleAccountId;
    @JsonProperty("role_id")
    @NotNull
    Long roleId;

}
