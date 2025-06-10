package com.iamdvh.shop_app.controllers;

import com.iamdvh.shop_app.dtos.UserDTO;
import com.iamdvh.shop_app.dtos.UserLoginDTO;
import com.iamdvh.shop_app.services.IUserService;
import com.iamdvh.shop_app.services.imp.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDTO user, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!user.getPassword().equals(user.getRetypePassword())) {
                return ResponseEntity.badRequest().body("Passwords do not match");
            }
            return ResponseEntity.ok().body(userService.createUser(user));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDTO user) {
        try {
            return ResponseEntity.ok().body(userService.login(user.getPhoneNumber(), user.getPassword()));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
