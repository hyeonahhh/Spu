package com.example.spu.OAuth2;

import com.example.spu.Enum.Authority;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOauth2User extends DefaultOAuth2User {
    private String email;
    private Authority authority;

    /**
     * Constructs a {@code DefaultOAuth2User} using the provided parameters.
     *
     * @param authorities      the authorities granted to the user
     * @param attributes       the attributes about the user
     * @param nameAttributeKey the key used to access the user's &quot;name&quot; from
     *                         {@link #getAttributes()}
     */
    // 사용자 추가 정보가 필요하니까 필요한 클래스
    public CustomOauth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey,
                            String email, Authority authority) {
        super(authorities, attributes, nameAttributeKey);
        this.email = email;
        this.authority = authority;
    }
}


