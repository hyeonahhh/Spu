package com.example.spu.Repository;

import com.example.spu.Enum.Platform;
import com.example.spu.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAllByOrderByIdDesc(Pageable pageable);

    Optional<User> findBySpuId(String spuId);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByPlatformAndSocialId(Platform platform, String socialId);

    boolean existsByEmail(String email);

    void deleteBySpuId(String spuId);
}
