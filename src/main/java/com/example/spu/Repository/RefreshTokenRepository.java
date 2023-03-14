package com.example.spu.Repository;

import com.example.spu.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    
    // user id 값으로 토큰 가져오기
    Optional<RefreshToken> findByKey(String key);
}
