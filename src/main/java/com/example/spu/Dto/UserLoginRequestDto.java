package com.example.spu.Dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginRequestDto {
    @NotBlank
    private String spuId;
    @NotBlank
    private String password;
}
