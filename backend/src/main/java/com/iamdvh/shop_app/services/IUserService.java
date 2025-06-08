package com.iamdvh.shop_app.services;

import com.iamdvh.shop_app.dtos.UserDTO;
import com.iamdvh.shop_app.entities.User;

public interface IUserService {
    User createUser(UserDTO userDTO);
    String login(String phoneNumber, String password);
}
