package com.example.spu.Service;

import com.example.spu.Dto.*;
import com.example.spu.Jwt.JwtTokenProvider;
import com.example.spu.Repository.UserRepository;

import com.example.spu.Util.SecurityUtil;
import com.example.spu.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    ModelMapper modelMapper;

    @Transactional
    @Override
    public ResponseEntity<?> signUp(UserSignUpRequestDto requestDto) throws Exception {
        if (userRepository.findBySpuId(requestDto.getSpuId()).isPresent()) {
            return ResponseEntity.badRequest().body("이미 사용중인 아이디입니다.");
        }

        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("이미 회원가입된 이메일입니다.");
        }

        User user = userRepository.save(requestDto.toEntity());
        user.encodePassword(passwordEncoder);
        return ResponseEntity.ok(modelMapper.map(user, UserDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> login(UserLoginRequestDto userLoginRequestDto){
        if (userLoginRequestDto == null) {
            return ResponseEntity.badRequest().body("잘못된 로그인 정보입니다.");
        }
        // id, pw를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginRequestDto.getSpuId(), userLoginRequestDto.getPassword());

        // 실제 검증이 이루어짐
        // authenticate 메서드가 실행될 때 customUserDetailsService에서 만든 loadUserByUsername메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = jwtTokenProvider.createToken(authentication);

        redisTemplate.opsForValue()
                .set("RefreshToken:" + authentication.getName(), tokenDto.getRefreshToken()
                        , tokenDto.getAccessTokenExpiresIn() - new Date().getTime(), TimeUnit.MILLISECONDS);
        return ResponseEntity.ok(tokenDto);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> reissue(TokenRequestDto tokenRequestDto) {
        // Refresh Token 검증
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            return ResponseEntity.badRequest().body("Refresh Token이 유효하지 않습니다.");
        }

        // Access Token 에서 Member ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        String refreshToken = (String) redisTemplate.opsForValue().get("RefreshToken:" + authentication.getName());
        // 로그아웃되어 존재하지 않는다면
        if(ObjectUtils.isEmpty(refreshToken)) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
        if (!refreshToken.equals(tokenRequestDto.getRefreshToken())) {
            return ResponseEntity.badRequest().body("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 새로운 토큰 생성
        TokenDto tokenDto = jwtTokenProvider.createToken(authentication);

        // 정보 업데이트
        redisTemplate.opsForValue()
                .set("RefreshToken:" + authentication.getName(), tokenDto.getRefreshToken(),
                        tokenDto.getAccessTokenExpiresIn(), TimeUnit.MILLISECONDS);
        // 토큰 발급
        return ResponseEntity.ok(tokenDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity logout(TokenRequestDto requestDto) {
        // 1. Access Token 검증
        if (!jwtTokenProvider.validateToken(requestDto.getAccessToken())) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }

        // 2. Access Token 에서 User spu id 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(requestDto.getAccessToken());

        // 3. Redis 에서 해당 User spu id 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get("RefreshToken:" + authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RefreshToken:" + authentication.getName());
        }

        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = jwtTokenProvider.getExpiration(requestDto.getAccessToken());
        redisTemplate.opsForValue()
                .set(requestDto.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    @Transactional
    public void deleteUser(){
        Optional<User> user = userRepository.findBySpuId(SecurityUtil.getCurrentUserId());
        if (user.isEmpty()) {
            throw new IllegalStateException("사용자가 존재하지 않습니다.");
        }

        userRepository.deleteById(user.get().getId());
    }


}
