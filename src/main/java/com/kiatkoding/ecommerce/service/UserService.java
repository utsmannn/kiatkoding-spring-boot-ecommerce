package com.kiatkoding.ecommerce.service;

import com.kiatkoding.ecommerce.model.dto.UserDTO;
import com.kiatkoding.ecommerce.model.entity.UserEntity;
import com.kiatkoding.ecommerce.model.request.RegisterRequest;
import com.kiatkoding.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO userEntity() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        String phoneNumber = userDetails.getUsername();
        return userRepository.findByPhoneNumber(phoneNumber)
                .map(this::mapFromEntity)
                .orElseThrow();
    }

    private UserDTO mapFromEntity(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setPhoneNumber(userEntity.getPhoneNumber());
        return userDTO;
    }


}
