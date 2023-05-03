package com.example.spu.Controller;

import com.example.spu.Dto.UserDto;
import com.example.spu.Dto.UserSearchCondition;
import com.example.spu.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<Page<UserDto>> getAllUsers(@PageableDefault(size = 10, sort = "id",  direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUser(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    // 이메일로 사용자 조회
    @GetMapping("/search/email")
    public ResponseEntity<String> searchIdByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.searchIdByEmail(email));
    }

    // 핸드폰 번호로 사용자 조회
    @GetMapping("/search/phone")
    public ResponseEntity<String> searchIdByPhone(@RequestParam String phone) {
        return ResponseEntity.ok(userService.searchIdByPhoneNumber(phone));
    }

    @PutMapping("/update/password")
    public void updatePassword(@RequestBody HashMap<String, String> map) {
        userService.updatePassword(map);
    }

    // 프로필 비공개 or 공개 수정
    @PutMapping("/update/public/{id}")
    public void updatePublicState(@PathVariable Long id, @RequestParam boolean state) {
        userService.updatePublicState(id, state);
    }
}
