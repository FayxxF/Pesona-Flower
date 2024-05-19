package com.clover.pesonaflower.service;

import com.clover.pesonaflower.dto.RegistrationDto;
import com.clover.pesonaflower.models.UserEntity;
import jakarta.servlet.Registration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserEntity findAdmin(String userName);

    void saveUser(RegistrationDto registrationDto);

    UserEntity findByUsername(String username);
}
