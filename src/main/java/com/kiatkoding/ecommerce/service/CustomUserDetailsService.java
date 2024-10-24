package com.kiatkoding.ecommerce.service;

import com.kiatkoding.ecommerce.model.CustomUserDetails;
import com.kiatkoding.ecommerce.model.entity.UserEntity;
import com.kiatkoding.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByPhoneNumber(username)
                .map(this::mapFromEntity)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
    }

    private CustomUserDetails mapFromEntity(UserEntity userEntity) {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUsername(userEntity.phoneNumber);
        customUserDetails.setPassword(userEntity.password);
        customUserDetails.setId(userEntity.id);
        customUserDetails.setName(userEntity.name);
        return customUserDetails;
    }
}
