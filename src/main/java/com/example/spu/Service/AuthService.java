package com.example.spu.Service;

import com.example.spu.Dto.*;

public interface AuthService {

    UserDto signUp(UserSignUpRequestDto requestDto) throws Exception;
    TokenDto login(UserLoginRequestDto userLoginRequestDto);
    TokenDto reissue(TokenRequestDto tokenRequestDto);
}
