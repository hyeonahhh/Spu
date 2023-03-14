package com.example.spu.Dto;

import com.example.spu.Enum.Authority;
import com.example.spu.Enum.Platform;
import com.example.spu.model.Like;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    @NotBlank
    private String spuId;

    @NotBlank
    private String password;

    private Platform platform;

    @NotBlank
    private String email;

    @NotBlank
    private String birth;

    @NotBlank
    private String name;

    @NotBlank
    private String phoneNumber;

    private boolean isPublic;

    private int followNum;
    private int followerNum;

    private Like like;
    private Authority authority;
}
