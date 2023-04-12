package com.example.spu.Controller;


import com.example.spu.Dto.*;
import com.example.spu.Service.AuthService;
import com.example.spu.Service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/signup")
    public void signup(@Valid @RequestBody UserSignUpRequestDto requestDto) throws Exception {
        authService.signUp(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserLoginRequestDto requestDto) throws Exception {
        return authService.login(requestDto);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return authService.reissue(tokenRequestDto);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String accessToken,
                       @RequestHeader("RefreshToken") String refreshToken) {
        return authService.logout(TokenRequestDto.builder().accessToken(accessToken.substring(7)).refreshToken(refreshToken).build());
    }

    @DeleteMapping
    public ResponseEntity<String> deleterUser(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);
        String refreshToken = request.getHeader("RefreshToken");
        authService.logout(TokenRequestDto.builder().accessToken(accessToken).refreshToken(refreshToken).build());
        authService.deleteUser();
        return ResponseEntity.ok("회원탈퇴가 완료되었습니다.");
    }

    @PostMapping("/email/confirm")
    public ResponseEntity<String> emailConfirm(@RequestParam String email) throws Exception {
        return ResponseEntity.ok(emailService.sendSimpleMessage(email));
    }
}
