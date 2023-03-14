package com.example.spu.Dto;

import com.example.spu.Enum.Authority;
import com.example.spu.Enum.Platform;
import com.example.spu.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 플랫폼으로 X. 기본 회원가입
public class UserSignUpRequestDto {

    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 2, message = "2글자 이상이어야 합니다")
    private String spuId;

    @NotBlank(message = "이메일을 입력해주세요")
    @Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z]+[.]+[a-zA-z.]+", message = "이메일 형식에 맞지 않습니다")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "이름을 입력해주세요")
    @Size(min = 2, message = "2글자 이상이어야 합니다")
    private String name;

    @NotBlank
    private String birth;

    @NotBlank
    private String phoneNumber;

    private Authority authority;

    @Builder
    public User toEntity() {
        return User.builder()
                .spuId(spuId)
                .password(password)
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .birth(birth)
                .authority(Authority.USER)
                .followerNum(0)
                .followNum(0)
                .isPublic(true)
                .like(null)
                .platform(Platform.NONE)
                .build();
    }
}
