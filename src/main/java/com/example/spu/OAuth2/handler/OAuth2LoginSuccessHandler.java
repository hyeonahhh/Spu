package com.example.spu.OAuth2.handler;

import com.example.spu.Dto.TokenDto;
import com.example.spu.Enum.Authority;
import com.example.spu.Jwt.JwtTokenProvider;
import com.example.spu.OAuth2.CustomOauth2User;
import com.example.spu.Repository.UserRepository;
import com.example.spu.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 login 성공");
        try {
            CustomOauth2User customOauth2User = (CustomOauth2User) authentication.getPrincipal();

            // 처음 로그인한 유저
            if (customOauth2User.getAuthority() == Authority.GUEST) {
                TokenDto tokenDto = jwtTokenProvider.createToken(authentication);
                response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
//                response.sendRedirect("oauth2/sign-up"); // 프론트의 회원가입 추가 정보 입력으로 이동

                response.setHeader("Authorization", tokenDto.getAccessToken());
                response.setHeader("RefreshToken", tokenDto.getRefreshToken());

                User findUser = userRepository.findByEmail(customOauth2User.getEmail())
                        .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 유저가 없습니다. "));
                findUser.authorizeUser(); // GUEST를 USER로 변경
                redisTemplate.opsForValue()
                        .set("RefreshToken:" + authentication.getName(), tokenDto.getRefreshToken()
                                , tokenDto.getAccessTokenExpiresIn() - new Date().getTime(), TimeUnit.MILLISECONDS);

            } else {
                loginSuccess(response, authentication);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void loginSuccess(HttpServletResponse response, Authentication authentication) {
        TokenDto tokenDto = jwtTokenProvider.createToken(authentication);

        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("RefreshToken", "Bearer " + tokenDto.getRefreshToken());

        response.setHeader("Authorization", tokenDto.getAccessToken());
        response.setHeader("RefreshToken", tokenDto.getRefreshToken());

        redisTemplate.opsForValue()
                .set("RefreshToken:" + authentication.getName(), tokenDto.getRefreshToken()
                        , tokenDto.getAccessTokenExpiresIn() - new Date().getTime(), TimeUnit.MILLISECONDS);

    }
}
