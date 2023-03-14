package com.example.spu.Repository;

import com.example.spu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySpuId(String spuId);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsBySpuId(String spuId);
    boolean existsByEmail(String email);
}
