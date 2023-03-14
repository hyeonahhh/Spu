package com.example.spu.Service;

import com.example.spu.Dto.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> signUp(UserSignUpRequestDto requestDto) throws Exception;
    TokenDto login(UserLoginRequestDto userLoginRequestDto);
    ResponseEntity<?> reissue(TokenRequestDto tokenRequestDto);
    ResponseEntity logout(TokenRequestDto tokenDto);
}
