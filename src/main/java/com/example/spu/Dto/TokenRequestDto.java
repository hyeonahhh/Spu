package com.example.spu.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequestDto {

    String accessToken;
    String refreshToken;
}
