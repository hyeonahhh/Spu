package com.example.spu.Service;

import com.example.spu.Dto.UserDto;
import com.example.spu.Dto.UserSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;

public interface UserService {
    Page<UserDto> getAllUser(Pageable pageable);

    Page<UserDto> searchUser(UserSearchCondition condition, Pageable pageable);

    UserDto findById(Long id);
    UserDto updateUser(UserDto userDto);

    String searchIdByEmail(String email);
    String searchIdByPhoneNumber(String phoneNumber);

    void updatePassword(HashMap<String, String> map);

    void updatePublicState(Long id, boolean state);
}
