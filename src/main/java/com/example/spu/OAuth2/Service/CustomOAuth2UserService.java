package com.example.spu.OAuth2.Service;

import com.example.spu.Enum.Platform;
import com.example.spu.OAuth2.CustomOauth2User;
import com.example.spu.OAuth2.OAuthAttributes;
import com.example.spu.Repository.UserRepository;
import com.example.spu.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    private static final String NAVER = "naver";
    private static final String KAKAO = "kakao";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Platform platform = getPlatformType(registrationId);
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthAttributes extracAttributes = OAuthAttributes.of(platform, userNameAttributeName, attributes);

        User createdUser = getUser(extracAttributes, platform);

        return new CustomOauth2User(
                Collections.singleton(new SimpleGrantedAuthority(createdUser.getAuthority().name())),
                        attributes,
                        extracAttributes.getNameAttributeKey(),
                        createdUser.getEmail(),
                        createdUser.getAuthority()
        );
    }

    private Platform getPlatformType(String registrationId) {
        if(NAVER.equals(registrationId)) {
            return Platform.NAVER;
        }
        if(KAKAO.equals(registrationId)) {
            return Platform.KAKAO;
        }
        return Platform.GOOGLE;
    }

    private User getUser(OAuthAttributes attributes, Platform platform) {
        User findUser = userRepository.findByPlatformAndSocialId(platform,
                attributes.getOauth2UserInfo().getId()).orElse(null);

        if(findUser == null) {
            return saveUser(attributes, platform);
        }
        return findUser;
    }

    private User saveUser(OAuthAttributes attributes, Platform socialType) {
        User createdUser = attributes.toEntity(socialType, attributes.getOauth2UserInfo());
        return userRepository.save(createdUser);
    }
}
