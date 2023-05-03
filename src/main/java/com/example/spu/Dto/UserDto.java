package com.example.spu.Dto;

import com.example.spu.Enum.Authority;
import com.example.spu.Enum.Platform;
import com.example.spu.model.Enquiry;
import com.example.spu.model.Review;
import com.example.spu.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

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

    private String socialId;

    @NotBlank
    private String email;

    @NotBlank
    private String birth;

    @NotBlank
    private String name;

    @NotBlank
    private String phoneNumber;

    private String imageUrl;

    private boolean isPublic;

    private int followNum;
    private int followerNum;

    private PreferencesDto preferences;
    private Authority authority;

    private List<Enquiry> enquiryList = new ArrayList<>();
    private List<Review> reviewList = new ArrayList<>();

    @Builder
    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .spuId(user.getSpuId())
                .password(user.getPassword())
                .platform(user.getPlatform())
                .socialId(user.getSocialId())
                .email(user.getEmail())
                .birth(user.getBirth())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .imageUrl(user.getImageUrl())
                .isPublic(user.isPublic())
                .followNum(user.getFollowNum())
                .followerNum(user.getFollowerNum())
                .authority(user.getAuthority())
                .build();
    }
}
