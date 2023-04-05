package com.example.spu.Service;

import com.example.spu.Dto.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    void signUp(UserSignUpRequestDto requestDto) throws Exception;
    ResponseEntity<TokenDto> login(UserLoginRequestDto userLoginRequestDto);
    ResponseEntity<TokenDto> reissue(TokenRequestDto tokenRequestDto);
    ResponseEntity logout(TokenRequestDto tokenDto);
    void deleteUser();
}
