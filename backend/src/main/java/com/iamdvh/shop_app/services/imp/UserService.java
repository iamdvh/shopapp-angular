package com.iamdvh.shop_app.services.imp;

import com.iamdvh.shop_app.componants.JwtTokenUtils;
import com.iamdvh.shop_app.dtos.UserDTO;
import com.iamdvh.shop_app.exceptions.DataNotFoundException;
import com.iamdvh.shop_app.entities.Role;
import com.iamdvh.shop_app.entities.User;
import com.iamdvh.shop_app.repositories.RoleRepository;
import com.iamdvh.shop_app.repositories.UserRepository;
import com.iamdvh.shop_app.services.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    JwtTokenUtils jwtTokenUtils;
    AuthenticationManager authenticationManager;
    @Override
    public User createUser(UserDTO userDTO) {
        try {

            String phoneNumber = userDTO.getPhoneNumber();
            if(userRepository.existsByPhoneNumber(phoneNumber)) {
                throw  new DataIntegrityViolationException("Phone number already exists");
            }
            Role role =  roleRepository.findById( userDTO.getRoleId())
                    .orElseThrow(() -> new DataNotFoundException("Role not found"));
            User user = User.builder()
                    .fullName(userDTO.getFullname())
                    .phoneNumber(userDTO.getPhoneNumber())
                    .dateOfBirth(userDTO.getDateOfBirth())
                    .address(userDTO.getAddress())
                    .role(role)
                    .facebookAccountId(userDTO.getFacebookAccountId())
                    .googleAccountId(userDTO.getGoogleAccountId())
                    .build();
            if(userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
                String password = userDTO.getPassword();
                String encodedPassword = passwordEncoder.encode(password);
                user.setPassword(encodedPassword);
            }
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String login(String phoneNumber, String password) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new DataNotFoundException("Invalid phone number / password"));
        if(user.getGoogleAccountId() == 0 && user.getFacebookAccountId() == 0) {
            if(!passwordEncoder.matches(password, user.getPassword())) {
                throw new DataNotFoundException("Invalid password");
            }
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getPhoneNumber(), password);
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtils.generateToken(user);
    }
}
