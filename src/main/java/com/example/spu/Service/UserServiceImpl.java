package com.example.spu.Service;

import com.example.spu.Dto.PreferencesDto;
import com.example.spu.Dto.UserDto;
import com.example.spu.Dto.UserSearchCondition;
import com.example.spu.Repository.UserRepository;
import com.example.spu.Repository.UserRepositoryCustom;
import com.example.spu.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRepositoryCustom userRepositoryCustom;

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<UserDto> getAllUser(Pageable pageable) {
        Page<User> userPage = userRepository.findAllByOrderByIdDesc(pageable);
        return userPage.map(UserDto::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> searchUser(UserSearchCondition condition, Pageable pageable) {
        return userRepositoryCustom.search(condition, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        UserDto userDto = modelMapper.map(user, UserDto.class);

        if (user.getPreferences() != null) {
            userDto.setPreferences(modelMapper.map(user.getPreferences(), PreferencesDto.class));
        }
        return userDto;
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto) {
        Optional<User> user = userRepository.findById(userDto.getId());
        if (user.isEmpty()) {
            throw new IllegalStateException();
        }
        user.ifPresent(selectUser -> {
            selectUser.setBirth(userDto.getBirth());
            selectUser.setEmail(userDto.getEmail());
            selectUser.setImageUrl(userDto.getImageUrl());
            selectUser.setName(userDto.getName());
            selectUser.setPhoneNumber(userDto.getPhoneNumber());
            selectUser.setPreferences(userDto.getPreferences().toEntity());
        });
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public String searchIdByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new IllegalStateException("해당하는 사용자가 없습니다.");
        }
        return user.get().getSpuId();
    }

    @Override
    @Transactional(readOnly = true)
    public String searchIdByPhoneNumber(String phoneNumber) {
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        if (user.isEmpty()) {
            throw new IllegalStateException("해당하는 사용자가 없습니다.");
        }
        return user.get().getSpuId();
    }

    @Override
    @Transactional
    public void updatePassword(HashMap<String, String> map) {
        User user = userRepository.findBySpuId(map.get("id"))
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));
        user.setPassword(map.get("password"));
        user.encodePassword(passwordEncoder);
    }

    @Override
    @Transactional
    public void updatePublicState(Long id, boolean state) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));
        user.setPublic(state);
    }

}
