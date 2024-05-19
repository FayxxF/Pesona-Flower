package com.clover.pesonaflower.repository;

import com.clover.pesonaflower.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String userName);
}
